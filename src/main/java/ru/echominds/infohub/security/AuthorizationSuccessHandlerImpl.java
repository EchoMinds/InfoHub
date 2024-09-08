package ru.echominds.infohub.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import ru.echominds.infohub.security.jwt.JwtService;

import java.io.IOException;

@Service
public class AuthorizationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    private final OAuthTokenToProfileConvertStrategy
            googleTokenStrategy = new GoogleTokenStrategy();

    private final JwtService jwtService;

    public AuthorizationSuccessHandlerImpl(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken auth = (OAuth2AuthenticationToken) authentication;
        var userInfoFromToken = extractInfoFromToken(auth, auth.getAuthorizedClientRegistrationId());
        var generatedToken = jwtService.generateToken(userInfoFromToken);
        System.out.println(generatedToken);
        response.addHeader("Authorization", "Bearer " + generatedToken);
    }

    private UserInfoFromToken extractInfoFromToken(OAuth2AuthenticationToken token, String clientId) {
        return switch (clientId) {
//            case "github" -> githubTokenStrategy.extractInfoFromToken(token);
            case "google" -> googleTokenStrategy.extractInfoFromToken(token);
            default -> throw new Error("");
        };
    }

    public record UserInfoFromToken(
            String userId,
            String userName,
            String userEmail,
            String userProfilePicture,
            String userRole
    ) {
    }
}
