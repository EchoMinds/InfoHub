package ru.echominds.infohub.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.echominds.infohub.convertors.ArticleConvertor;
import ru.echominds.infohub.domain.Article;
import ru.echominds.infohub.domain.User;
import ru.echominds.infohub.dtos.ArticleDTO;
import ru.echominds.infohub.exceptions.ArticleNotFoundException;
import ru.echominds.infohub.exceptions.UserNotFoundException;
import ru.echominds.infohub.repositories.ArticleRepository;
import ru.echominds.infohub.repositories.UserRepository;
import ru.echominds.infohub.security.SecurityAuthorizationManager;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleConvertor articleConvertor;
    private final UserRepository userRepository;
    private final SecurityAuthorizationManager authorizationManager;

    public ArticleDTO getArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(ArticleNotFoundException::new);

        Long totalRating = articleRepository.getArticleRatingById(article.getId());

        return articleConvertor.convertArticleToArticleDTO(article, totalRating);
    }

    public List<ArticleDTO> getAllArticles(PageRequest pageRequest) {
        return articleRepository.findAll(pageRequest).stream().map(x -> articleConvertor.convertArticleToArticleDTO(
                x, articleRepository.getArticleRatingById(x.getId())
        )).toList();
    }

    // check auth
    public void createArticle(ArticleDTO articleDTO) {

        if (authorizationManager.getCurrentUser() == null) throw new UserNotFoundException();

        User author = userRepository.findById(articleDTO.userDTO().id()).orElseThrow(UserNotFoundException::new);

        articleRepository.save(articleConvertor.convertArticleDTOtoArticle(articleDTO, author));
    }

    public void updateArticle(Long id, ArticleDTO updatedArticleDTO) {
        Article article = articleRepository.findById(id).orElseThrow(ArticleNotFoundException::new);

        authorizationManager.getAccessForArticle(article);

        article.setTitle(updatedArticleDTO.title() == null ? article.getTitle() : updatedArticleDTO.title());
        article.setViews(updatedArticleDTO.views() == null ? article.getViews() : updatedArticleDTO.views());
        article.setText(updatedArticleDTO.text() == null ? article.getText() : updatedArticleDTO.text());

        articleRepository.save(article);
    }

    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(ArticleNotFoundException::new);

        authorizationManager.getAccessForArticle(article);

        //delete article on user data
        User author = userRepository.findById(article.getUser().getId()).orElseThrow(UserNotFoundException::new);
        author.getArticles().remove(article);
        userRepository.save(author);

        articleRepository.delete(article);
    }
}