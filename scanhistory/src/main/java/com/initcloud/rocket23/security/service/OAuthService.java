package com.initcloud.rocket23.security.service;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiAuthException;
import com.initcloud.rocket23.infra.repository.UserRepository;
import com.initcloud.rocket23.security.config.SecurityProperties;
import com.initcloud.rocket23.security.dto.OAuthDto;
import com.initcloud.rocket23.security.dto.Token;
import com.initcloud.rocket23.security.facade.OAuthRequestFacade;
import com.initcloud.rocket23.user.dto.AuthRequestDto;
import com.initcloud.rocket23.user.entity.User;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthService {

    private final UserRepository userRepository;
    private final OAuthRequestFacade oauthRequestFacade;
    private final SecurityProperties properties;
    private final Environment environment;

    public void redirectGithub(HttpServletResponse response, String redirect) {
        try {
            String url = oauthRequestFacade.getRedirectAuthUrl(redirect);
            response.sendRedirect(url);
        } catch (IOException e) {
            throw new ApiAuthException(ResponseCode.INVALID_CREDENTIALS);
        }
    }

    public OAuthDto.GithubTokenResponse getAccessToken(AuthRequestDto request) {
        AuthRequestDto dto = new AuthRequestDto(
                environment.getProperty("GITHUB_CLIENT_ID"),
                environment.getProperty("GITHUB_CLIENT_SECRET"),
                request.getCode(),
                environment.getProperty("REDIRECT_URI"));

        return oauthRequestFacade.requestGithubOAuthToken(dto);
    }

    public Token getUserAccessToken(AuthRequestDto request) {
        OAuthDto.GithubTokenResponse tokenResponse = getAccessToken(request);
        OAuthDto.GithubUserDetail userDetail = oauthRequestFacade.requestGithubUserDetail(
                tokenResponse.getAccessToken());

        User user = getUserIfExist(userDetail);

        return oauthRequestFacade.createSocialUserToken(user.getUsername());
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
