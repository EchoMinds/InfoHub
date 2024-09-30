package ru.echominds.infohub.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ru.echominds.infohub.convertors.ArticleConvertor;
import ru.echominds.infohub.domain.Article;
import ru.echominds.infohub.domain.Role;
import ru.echominds.infohub.domain.User;
import ru.echominds.infohub.dtos.ArticleDTO;
import ru.echominds.infohub.exceptions.ArticleNotFoundException;
import ru.echominds.infohub.exceptions.UserNotAuthorArticle;
import ru.echominds.infohub.exceptions.UserNotFoundException;
import ru.echominds.infohub.repositories.ArticleRepository;
import ru.echominds.infohub.repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleConvertor articleConvertor;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken) && auth != null) {
            //get curUser
            OAuth2User curUser = (OAuth2User) auth.getPrincipal();
            String email = curUser.getAttribute("email");

            return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        }

        return null;
    }

    private void getAccess(Article article) {

        if (getCurrentUser() == null) throw new UserNotFoundException();
        if (!getCurrentUser().getId().equals(article.getUser().getId()) ||
                !(getCurrentUser().getRoles().contains(Role.ADMINISTRATOR)
                        && getCurrentUser().getRoles().contains(Role.HEAD_ADMINISTRATOR))) {
            throw new UserNotAuthorArticle();
        }
    }

    public ArticleDTO getArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(ArticleNotFoundException::new);

        Long totalRating = articleRepository.getArticleRatingById(article.getArticleId());

        return articleConvertor.convertArticleToArticleDTO(article, totalRating);
    }

    public List<ArticleDTO> getAllArticles(PageRequest pageRequest) {
        return articleRepository.findAll(pageRequest).stream().map(x -> articleConvertor.convertArticleToArticleDTO(
                x, articleRepository.getArticleRatingById(x.getArticleId())
        )).toList();
    }

    // check auth
    public void createArticle(ArticleDTO articleDTO) {

        if (getCurrentUser() == null) throw new UserNotFoundException();

        User author = userRepository.findById(articleDTO.userDTO().id()).orElseThrow(UserNotFoundException::new);

        articleRepository.save(articleConvertor.convertArticleDTOtoArticle(articleDTO, author));
    }

    // добавить проверку на авторство или модерацию
    public void updateArticle(Long id, ArticleDTO updatedArticleDTO) {
        Article article = articleRepository.findById(id).orElseThrow(ArticleNotFoundException::new);

        getAccess(article);

        article.setTitle(updatedArticleDTO.title() == null ? article.getTitle() : updatedArticleDTO.title());
        article.setViews(updatedArticleDTO.views() == null ? article.getViews() : updatedArticleDTO.views());
        article.setText(updatedArticleDTO.text() == null ? article.getText() : updatedArticleDTO.text());

        articleRepository.save(article);
    }

    // добавить проверку на авторство или модерацию
    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(ArticleNotFoundException::new);

        getAccess(article);

        //delete article on user data
        User author = userRepository.findById(article.getUser().getId()).orElseThrow(UserNotFoundException::new);
        author.getArticles().remove(article);
        userRepository.save(author);

        articleRepository.delete(article);
    }
}
