package com.initcloud.rocket23.security.service;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiAuthException;
import com.initcloud.rocket23.infra.repository.UserRefreshTokenRepository;
import com.initcloud.rocket23.infra.repository.UserRepository;
import com.initcloud.rocket23.security.config.SecurityProperties;
import com.initcloud.rocket23.security.dto.OAuthDto;
import com.initcloud.rocket23.security.dto.Token;
import com.initcloud.rocket23.security.facade.OAuthRequestFacade;
import com.initcloud.rocket23.security.provider.JwtProvider;
import com.initcloud.rocket23.user.dto.AuthRequestDto;
import com.initcloud.rocket23.user.entity.User;
import com.initcloud.rocket23.user.entity.UserRefreshToken;
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
    private final UserRefreshTokenRepository userRefreshTokenRepository;

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

        User user = getGithubUserIfExist(userDetail);
        Token token = oauthRequestFacade.createSocialUserToken(user.getUsername());
        UserRefreshToken userToken = UserRefreshToken.builder()
                .user(user)
                .refreshToken(token.getRefreshToken())
                .build();
        userRefreshTokenRepository.save(userToken);

        return token;
    }

    public Token createUserAccessToken(AuthRequestDto.loginDto dto) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(dto.getUsername());
        if (!passwordEncoder.matches(dto.getPassword(), userDetails.getPassword())) {
            throw new ApiAuthException(ResponseCode.INVALID_CREDENTIALS);
        }

        User user = getUserIfExist(userDetails);

        Token token = oauthRequestFacade.createSocialUserToken(userDetails.getUsername());

        // 기존 refreshToken을 가져오거나 새로 생성
        Optional<UserRefreshToken> existingTokenOptional = userRefreshTokenRepository.findByUser(user);
        UserRefreshToken userRefreshToken;

        if (existingTokenOptional.isPresent()) {
            userRefreshToken = existingTokenOptional.get();
            userRefreshToken.updateRefreshToken(token.getRefreshToken());
        } else {
            userRefreshToken = UserRefreshToken.builder()
                    .user(user)
                    .refreshToken(token.getRefreshToken())
                    .build();
        }
        userRefreshTokenRepository.save(userRefreshToken);
        return token;
    }

    public Token reIssueAccessToken(String accessToken, String refreshToken){

        //access Token이 만료되있는지 확인하기
        if(!jwtProvider.validateTokenExceptExpiration(accessToken))
            throw new ApiAuthException(ResponseCode.INVALID_CREDENTIALS);

        //refresh Token 유효기간 확인하기
        if(jwtProvider.validateTokenExceptExpiration(refreshToken))
            throw new ApiAuthException(ResponseCode.INVALID_CREDENTIALS);

        //refresh Token 값 일치 확인하기
        getUserByRefreshToken(refreshToken);

        //access token 재발급하기
        return jwtProvider.refreshAccessToken(refreshToken, properties.getSecret());

    }

    public void logout(String refreshToken) {
        // refreshToken 무효화
        Optional<UserRefreshToken> userRefreshToken = userRefreshTokenRepository.findByRefreshToken(refreshToken);
        if(userRefreshToken.isPresent()){
            UserRefreshToken token = userRefreshToken.get();
            token.updateRefreshToken(" "); // null값으로 처리
            userRefreshTokenRepository.save(token);
        }else{
            throw new ApiAuthException(ResponseCode.INVALID_CREDENTIALS);
        }

    }

    private void getUserByRefreshToken(String refreshToken) {
        // Refresh Token 값으로 UserRefreshToken 조회
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new ApiAuthException(ResponseCode.INVALID_CREDENTIALS));

        // JWT 내에 있는 username과 UserRefreshToken에 저장된 username 비교
        String usernameFromToken = jwtProvider.getUsername(refreshToken, properties.getSecret());
        if (!userRefreshToken.getUser().getUsername().equals(usernameFromToken)) {
            throw new ApiAuthException(ResponseCode.INVALID_CREDENTIALS);
        }
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
    public User getGithubUserIfExist(OAuthDto.GithubUserDetail userDetail) {
        Optional<User> user = userRepository.findUserByUsername(userDetail.getLogin());

        if (user.isPresent()) {
            return user.get();
        }

        User socialUser = userRepository.save(User.addIndividualSocialUser(userDetail));

        initializeUserRules(socialUser);

        return socialUser;
    }

    /**
     * @return registered User
     */
    public User getUserIfExist(UserDetails userDetails) {
        return userRepository.findByUsername(userDetails.getUsername());
    }

}
