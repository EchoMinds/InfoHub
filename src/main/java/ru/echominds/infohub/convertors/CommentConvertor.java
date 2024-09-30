package ru.echominds.infohub.convertors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.echominds.infohub.domain.Article;
import ru.echominds.infohub.domain.Comment;
import ru.echominds.infohub.domain.User;
import ru.echominds.infohub.dtos.CommentDTO;
import ru.echominds.infohub.repositories.CommentRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentConvertor {

    private final CommentRepository commentRepository;

    public CommentDTO convertToDTO(Comment comment, List<Comment> commentList) {
        List<CommentDTO> subComments = commentList.stream()
                .filter(subComm -> subComm.getReplyTo() != null && subComm.getReplyTo().equals(comment.getId()))
                .map(subComm -> convertToDTO(subComm, commentList))
                .toList();

        return new CommentDTO(
                comment.getId(),
                comment.getUser().getId(),
                comment.getArticle().getArticleId(),
                comment.getReplyTo(),
                comment.getRating(),
                comment.getText(),
                subComments
        );
    }

    public static Comment toDomain(CommentDTO dto, User user, Article article) {
        Comment comment = new Comment(user, article, dto.text(), dto.rating());
        comment.setReplyTo(dto.replyTo());
        return comment;
    }
}
