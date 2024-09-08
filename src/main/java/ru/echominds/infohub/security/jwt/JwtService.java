package ru.echominds.infohub.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import ru.echominds.infohub.security.AuthorizationSuccessHandlerImpl;

import javax.crypto.SecretKey;

@Service
public class JwtService {
    private static final SecretKey key = Jwts.SIG.HS256.key().build();

    public String generateToken(AuthorizationSuccessHandlerImpl.UserInfoFromToken token) {
        return Jwts.builder().subject(token.userEmail()).signWith(key)
                .claim("profile_picture", token.userProfilePicture())
                .claim("id", token.userId())
                .claim("roles", token.userRole())
                .compact();
    }

    public Claims getClaims(String jwt) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();
    }


}
