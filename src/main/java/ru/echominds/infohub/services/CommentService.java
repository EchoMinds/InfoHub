package ru.echominds.infohub.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.echominds.infohub.convertors.CommentConvertor;
import ru.echominds.infohub.domain.Article;
import ru.echominds.infohub.domain.Comment;
import ru.echominds.infohub.domain.User;
import ru.echominds.infohub.dtos.CommentDTO;
import ru.echominds.infohub.dtos.UpdatedCommentDto;
import ru.echominds.infohub.exceptions.ArticleNotFoundException;
import ru.echominds.infohub.exceptions.CommentNotFoundException;
import ru.echominds.infohub.exceptions.UnauthorizedException;
import ru.echominds.infohub.exceptions.UserNotFoundException;
import ru.echominds.infohub.repositories.ArticleRepository;
import ru.echominds.infohub.repositories.CommentRepository;
import ru.echominds.infohub.repositories.UserRepository;
import ru.echominds.infohub.security.SecurityAuthorizationManager;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentConvertor commentConvertor;
    private final SecurityAuthorizationManager securityAuthorizationManager;

    public CommentDTO getCommentById(Long id) {
        return commentConvertor.convertToDTO(commentRepository.findById(id).orElseThrow(), commentRepository.findAll());
    }

    public List<CommentDTO> getAllCommentsForArticle(Long articleId, Pageable pageable) {
        return commentRepository.findCommentByArticleId(articleId, pageable).stream()
                .map(comment -> commentConvertor.convertToDTO(comment, commentRepository.findAll())).toList();
    }

    public void createComment(CommentDTO commentDTO) {
        User currentUser = securityAuthorizationManager.getCurrentUser();
        if (currentUser == null) {
            throw new UnauthorizedException();
        }

        User author = userRepository.findById(commentDTO.authorId()).orElseThrow(UserNotFoundException::new);
        Article currentArticle = articleRepository.findById(commentDTO.articleId())
                .orElseThrow(ArticleNotFoundException::new);

        Comment createdComment = commentConvertor.convertToDomain(commentDTO, author, currentArticle);

        commentRepository.save(createdComment);
    }

    //update text, UpRatingComment, DownRatingComment
    public void updateComment(Long id, UpdatedCommentDto updatedCommentDTO) {
        Comment updateableComment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);

        //security
        securityAuthorizationManager.getAccessForComment(updateableComment);

        updateableComment.setRating(
                updatedCommentDTO.rating() != null ? updatedCommentDTO.rating() : updateableComment.getRating());
        updateableComment.setText(
                updatedCommentDTO.text() != null ? updatedCommentDTO.text() : updateableComment.getText());

        commentRepository.save(updateableComment);
    }

    public void deleteComment(Long id) {
        Comment deleteableComment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);

        securityAuthorizationManager.getAccessForComment(deleteableComment);

        User author = userRepository.findById(deleteableComment.getUser().getId())
                .orElseThrow(UserNotFoundException::new);
        author.getComments().remove(deleteableComment);

        Article article = articleRepository.findById(deleteableComment.getArticle().getArticleId()).
                orElseThrow(ArticleNotFoundException::new);
        article.getComments().remove(deleteableComment);

        commentRepository.delete(deleteableComment);
    }
}
