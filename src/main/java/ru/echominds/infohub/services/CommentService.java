package ru.echominds.infohub.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.echominds.infohub.convertors.CommentConvertor;
import ru.echominds.infohub.dtos.CommentDTO;
import ru.echominds.infohub.repositories.CommentRepository;

@Service
@RequiredArgsConstructor
public class CommentService {
    /*
    getCommentById, getAllCommentsByArticleId
    createCommentForArticle, updateComment, deleteComment,
    UpRatingComment, DownRatingComment, ReplyToComment
     */

    private final CommentRepository commentRepository;
    private final CommentConvertor commentConvertor;

    public CommentDTO getCommentById(Long id) {
        return commentConvertor.convertToDTO(commentRepository.findById(id).orElseThrow(), commentRepository.findAll());
    }

}
