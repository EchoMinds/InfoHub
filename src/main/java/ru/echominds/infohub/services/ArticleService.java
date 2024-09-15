package ru.echominds.infohub.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.echominds.infohub.convertors.ArticleConvertor;
import ru.echominds.infohub.domain.Article;
import ru.echominds.infohub.domain.User;
import ru.echominds.infohub.dtos.ArticleDTO;
import ru.echominds.infohub.exceptions.ArticleNotFoundException;
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

    public ArticleDTO getArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(ArticleNotFoundException::new);

        Long totalRating = articleRepository.getArticleRatingById(article.getArticleId());

        return articleConvertor.convertArticleToArticleDTO(article, totalRating);
    }

    public List<ArticleDTO> getAllArticles() {
        return articleRepository.findAll().stream().map(x -> articleConvertor.convertArticleToArticleDTO(
                x, articleRepository.getArticleRatingById(x.getArticleId())
        )).toList();
    }

    public void updateArticle(Long id, ArticleDTO updatedArticleDTO) {
        Article article = articleRepository.findById(id).orElseThrow(ArticleNotFoundException::new);

        article.setTitle(updatedArticleDTO.title() == null ? article.getTitle() : updatedArticleDTO.title());
        article.setViews(updatedArticleDTO.views() == null ? article.getViews() : updatedArticleDTO.views());
        article.setText(updatedArticleDTO.text() == null ? article.getText() : updatedArticleDTO.text());

        articleRepository.save(article);
    }

    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(ArticleNotFoundException::new);
        articleRepository.delete(article);
    }

    public void createArticle(ArticleDTO articleDTO) {
        User author = userRepository.findById(articleDTO.userDTO().id()).orElseThrow(UserNotFoundException::new);

        articleRepository.save(articleConvertor.convertArticleDTOtoArticle(articleDTO, author));
    }
}
