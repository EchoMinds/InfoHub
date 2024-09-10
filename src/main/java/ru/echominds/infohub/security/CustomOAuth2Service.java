package ru.echominds.infohub.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ru.echominds.infohub.domain.User;
import ru.echominds.infohub.repositories.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // Выводим на чистую воду юзера
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");

        // проверка на наличие
        Optional<User> user = userRepository.findByEmail(email);

        User createdUser;
        if (user.isPresent()) {
            return delegate.loadUser(userRequest);
        } else {
            createdUser = new User();
            createdUser.setEmail(email);
            createdUser.setName(oAuth2User.getAttribute("name"));

            userRepository.save(createdUser);
        }

        return oAuth2User;
    }
}
