package ru.echominds.infohub.security;

import lombok.RequiredArgsConstructor;
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
import ru.echominds.infohub.exceptions.UserBannedException;
import ru.echominds.infohub.exceptions.UserNotFoundException;
import ru.echominds.infohub.repositories.UserRepository;

@Component
@RequiredArgsConstructor
public class SecurityAuthorizationManager {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            throw new UnauthorizedException();
        }

        try {
            OAuth2User curUser = (OAuth2User) auth.getPrincipal();
            String email = curUser.getAttribute("email");
            User currnetUser = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

            checkUserIsBanned(currnetUser);

            return currnetUser;
        } catch (ClassCastException | NullPointerException e) {
            throw new UnauthorizedException();
        }
    }

    public Object getCurrentUserOrAnonymous() {
        //check Anonymous
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if ((auth instanceof AnonymousAuthenticationToken) || auth == null) {
            return "Anonymous";
        }

        return getCurrentUser();
    }

    public void getAccessForArticle(Article article) {
        User currentUser = getCurrentUser();

        if (!currentUser.getId().equals(article.getUser().getId()) ||
                !(currentUser.getRoles().contains(Role.ADMINISTRATOR)
                        && currentUser.getRoles().contains(Role.HEAD_ADMINISTRATOR))) {
            throw new NoPermissionException();
        }
    }

    public void getAccessForComment(Comment comment) {
        User currentUser = getCurrentUser();

        if (!currentUser.getId().equals(comment.getUser().getId()) ||
                !(currentUser.getRoles().contains(Role.ADMINISTRATOR)
                        && currentUser.getRoles().contains(Role.HEAD_ADMINISTRATOR))) {
            throw new NoPermissionException();
        }
    }

    public void checkAdminRole() {
        User currentUser = getCurrentUser();

        if (!(currentUser.getRoles().contains(Role.ADMINISTRATOR)
                && currentUser.getRoles().contains(Role.HEAD_ADMINISTRATOR))) {
            throw new NoPermissionException();
        }
    }

    private void checkUserIsBanned(User user) {
        if (user.getIs_banned()) throw new UserBannedException();
    }
}
