package ru.echominds.infohub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.echominds.infohub.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.echominds.infohub.dtos.CommentDTO;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT * FROM comment WHERE article_id = :#{#id}", nativeQuery = true)
    Page<Comment> findCommentByArticleId(@Param("id") Long id, Pageable pageable);

    List<Comment> findAllByReplyTo(Long id);
}
