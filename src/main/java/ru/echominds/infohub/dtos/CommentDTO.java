package ru.echominds.infohub.dtos;

import java.util.List;

public record CommentDTO(
    Long id,
    Long authorId,
    Long articleId,
    Long replyTo,
    Long rating,
    String text,
    List<CommentDTO> subComments
) {
}
