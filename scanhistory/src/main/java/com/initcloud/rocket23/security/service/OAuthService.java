package com.initcloud.rocket23.security.service;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiAuthException;
import com.initcloud.rocket23.infra.repository.UserRepository;
import com.initcloud.rocket23.security.config.SecurityProperties;
import com.initcloud.rocket23.security.dto.OAuthDto;
import com.initcloud.rocket23.security.dto.Token;
import com.initcloud.rocket23.security.facade.OAuthRequestFacade;
import com.initcloud.rocket23.security.provider.JwtProvider;
import com.initcloud.rocket23.user.dto.AuthRequestDto;
import com.initcloud.rocket23.user.entity.User;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthService {

    private final UserRepository userRepository;
    private final OAuthRequestFacade oauthRequestFacade;
    private final SecurityProperties properties;
    private final Environment environment;

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final TokenStore tokenStore;
    private final JwtProvider jwtProvider;

    public void redirectGithub(HttpServletResponse response, String redirect) {
        try {
            String url = oauthRequestFacade.getRedirectAuthUrl(redirect);
            response.sendRedirect(url);
        } catch (IOException e) {
            throw new ApiAuthException(ResponseCode.INVALID_CREDENTIALS);
        }
    }

    public OAuthDto.GithubTokenResponse getAccessToken(AuthRequestDto.githubDto request) {
        AuthRequestDto.githubDto dto = new AuthRequestDto.githubDto(
                environment.getProperty("GITHUB_CLIENT_ID"),
                environment.getProperty("GITHUB_CLIENT_SECRET"),
                request.getCode(),
                environment.getProperty("REDIRECT_URI"));

        return oauthRequestFacade.requestGithubOAuthToken(dto);
    }

    public Token getUserAccessToken(AuthRequestDto.githubDto request) {
        OAuthDto.GithubTokenResponse tokenResponse = getAccessToken(request);
        OAuthDto.GithubUserDetail userDetail = oauthRequestFacade.requestGithubUserDetail(
                tokenResponse.getAccessToken());

        User user = getUserIfExist(userDetail);
        return oauthRequestFacade.createSocialUserToken(user.getUsername());
    }

    public Token createUserAccessToken(AuthRequestDto.loginDto dto) {
        UserDetails user = customUserDetailsService.loadUserByUsername(dto.getUsername());
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new ApiAuthException(ResponseCode.INVALID_CREDENTIALS);
        }

        return oauthRequestFacade.createSocialUserToken(user.getUsername());
    }

    public Token reIssueAccessToken(String accessToken, String refreshToken){

        //access Token이 만료되있는지 확인하기
        if(!jwtProvider.validateTokenExceptExpiration(accessToken))
            throw new ApiAuthException(ResponseCode.INVALID_CREDENTIALS);

        //refresh Token 유효기간 확인하기
        if(jwtProvider.validateTokenExceptExpiration(refreshToken))
            throw new ApiAuthException(ResponseCode.INVALID_CREDENTIALS);

        //access token 재발급하기
        return jwtProvider.refreshAccessToken(refreshToken, properties.getSecret());

    }

    public void logout(String accessToken, String refreshToken) {
        // accessToken 무효화
        tokenStore.removeAccessToken(tokenStore.readAccessToken(accessToken));

        // refreshToken 무효화
        tokenStore.removeRefreshToken(tokenStore.readRefreshToken(refreshToken));
    }

    /**
     * Todo
     * Initialize UsedRule from CustomRule for new User.
     */
    private void initializeUserRules(User user) {

    }

    /**
     * @return registered User
     */
    public User getUserIfExist(OAuthDto.GithubUserDetail userDetail) {
        Optional<User> user = userRepository.findUserByUsername(userDetail.getLogin());

        if (user.isPresent()) {
            return user.get();
        }

        User socialUser = userRepository.save(User.addIndividualSocialUser(userDetail));

        initializeUserRules(socialUser);

        return socialUser;
    }

}
