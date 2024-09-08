package ru.echominds.infohub.security.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.echominds.infohub.security.AuthToken;
import ru.echominds.infohub.security.Principal;

import java.io.IOException;
import java.util.List;


@Component
@RequiredArgsConstructor
public class JWTValidationFilter extends HttpFilter {


    private final JwtService jwtService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String token = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                Claims claims = jwtService.getClaims(token);
                var userEmail = claims.getSubject();
                String userId = (String) claims.get("id");
                var principal = new Principal(userEmail, userId);

                // Вот как тут с claims работать я еще точно не понял, есть идеи исто брать емейл и по нему глядеть.
                Authentication auth = new AuthToken(principal, (List<String>) claims.get("roles"));

                SecurityContextHolder.getContext().setAuthentication(auth);
                chain.doFilter(request, response);
            } catch (Exception e) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}