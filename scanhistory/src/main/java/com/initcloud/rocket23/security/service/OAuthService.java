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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthService {

    private final UserRepository userRepository;
    private final OAuthRequestFacade oauthRequestFacade;
    private final SecurityProperties properties;

    public void redirectGithub(HttpServletResponse response, String redirect) {
        try {
            String url = oauthRequestFacade.getRedirectAuthUrl(redirect);
            response.sendRedirect(url);
        } catch (IOException e) {
            throw new ApiAuthException(ResponseCode.INVALID_CREDENTIALS);
        }
    }

    public OAuthDto.GithubTokenResponse getAccessToken(AuthRequestDto request) {
        if (request == null
                || request.getClientId() == null
                || request.getClientSecret() == null
                || request.getCode() == null) {
            throw new ApiAuthException(ResponseCode.INVALID_REQUEST);
        }

        return oauthRequestFacade.requestGithubOAuthToken(request);
    }

    public Token getUserAccessToken(String clientId, String clientSecret, String code, String redirect) {
        AuthRequestDto request = new AuthRequestDto(clientId, clientSecret, code, redirect);
        OAuthDto.GithubTokenResponse tokenResponse = oauthRequestFacade.requestGithubOAuthToken(request);
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
