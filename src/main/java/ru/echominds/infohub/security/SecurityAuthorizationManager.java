package ru.echominds.infohub.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import ru.echominds.infohub.domain.Article;
import ru.echominds.infohub.domain.Comment;
import ru.echominds.infohub.domain.Role;
import ru.echominds.infohub.domain.User;
import ru.echominds.infohub.exceptions.NoPermissionException;
import ru.echominds.infohub.exceptions.UnauthorizedException;
import ru.echominds.infohub.exceptions.UserNotFoundException;
import ru.echominds.infohub.repositories.UserRepository;

@Component
@RequiredArgsConstructor
public class SecurityAuthorizationManager {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken) && auth != null) {
            //get curUser
            OAuth2User curUser = (OAuth2User) auth.getPrincipal();
            String email = curUser.getAttribute("email");

            return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        }

        return null;
    }

    public ResponseEntity<?> getCurrentUserOrAnonymous() {
        User user = getCurrentUser();

        if (user == null) {
            return new ResponseEntity<>("Anonymous", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    public void getAccessForArticle(Article article) {
        if (getCurrentUser() == null) throw new UnauthorizedException();
        if (!getCurrentUser().getId().equals(article.getUser().getId()) ||
                !(getCurrentUser().getRoles().contains(Role.ADMINISTRATOR)
                        && getCurrentUser().getRoles().contains(Role.HEAD_ADMINISTRATOR))) {
            throw new NoPermissionException();
        }
    }

    public void getAccessForComment(Comment comment) {
        if (getCurrentUser() == null) throw new UnauthorizedException();
        if (!getCurrentUser().getId().equals(comment.getUser().getId()) ||
                !(getCurrentUser().getRoles().contains(Role.ADMINISTRATOR)
                        && getCurrentUser().getRoles().contains(Role.HEAD_ADMINISTRATOR))) {
            throw new NoPermissionException();
        }
    }
}
