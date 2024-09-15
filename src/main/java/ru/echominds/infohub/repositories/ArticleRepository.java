package ru.echominds.infohub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.echominds.infohub.domain.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query(value = "SELECT SUM(value) FROM public.rating where article_id = :articleId ", nativeQuery = true)
    Long getArticleRatingById(Long articleId);
}
