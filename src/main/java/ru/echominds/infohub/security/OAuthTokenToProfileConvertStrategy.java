package ru.echominds.infohub.security;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public interface OAuthTokenToProfileConvertStrategy {
    AuthorizationSuccessHandlerImpl.UserInfoFromToken extractInfoFromToken(
            OAuth2AuthenticationToken authenticationToken);
}