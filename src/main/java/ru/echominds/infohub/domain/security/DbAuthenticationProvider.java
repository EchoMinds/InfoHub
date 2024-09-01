package ru.echominds.infohub.domain.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.echominds.infohub.domain.repositories.UserRepository;

@Component
@RequiredArgsConstructor
public class DbAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepository;
    /*
     * АЛЬФА ВАРИАНТ! НЕ В ПРОД!
     *  if (!"password".equals(password)) {
            throw new AuthenticationServiceException("Invalid username or password");
        }
     * Данный код вообще ждет что у всех пароль password,
     */

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final var password = authentication.getCredentials().toString();
        if (!"password".equals(password)) {
            throw new AuthenticationServiceException("Invalid username or password");
        }
        return userRepository.findByName(authentication.getName())
                .map(user -> new PlainAuthentication(user.getId()))
                .orElseThrow(() -> new AuthenticationServiceException("Invalid username or password"));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
