package ru.echominds.infohub.convertors;

import org.springframework.stereotype.Component;
import ru.echominds.infohub.domain.Article;
import ru.echominds.infohub.domain.User;
import ru.echominds.infohub.dtos.ArticleDTO;
import ru.echominds.infohub.dtos.UserDTO;

@Component
public class ArticleConvertor {
    public ArticleDTO convertArticleToArticleDTO(Article article,
                                                 Long articleRating) {
        return new ArticleDTO(
                new UserDTO(
                        article.getUser().getId(),
                        article.getUser().getName(),
                        article.getUser().getEmail(),
                        article.getUser().getAvatar()
                ),
                article.getTitle(),
                article.getText(),
                article.getViews(),
                articleRating
        );
    }

    public Article convertArticleDTOtoArticle(ArticleDTO articleDTO, User user) {
        return new Article(
                user, articleDTO.title(), articleDTO.text(), articleDTO.views()
        );
    }
}
