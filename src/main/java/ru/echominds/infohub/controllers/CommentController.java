package ru.echominds.infohub.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.echominds.infohub.dtos.CommentDTO;
import ru.echominds.infohub.dtos.UpdatedCommentDto;
import ru.echominds.infohub.services.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/comments/{commentId}")
    public CommentDTO getComment(@PathVariable Long commentId) {
        return commentService.getCommentById(commentId);
    }

    @PatchMapping("/comments/{commentId}")
    public HttpStatus updateComment(@PathVariable Long commentId,
                                    @RequestBody UpdatedCommentDto updatedCommentDto) {
        commentService.updateComment(commentId, updatedCommentDto);
        return HttpStatus.NO_CONTENT;
    }

    @DeleteMapping("/comments/{commentId}")
    public HttpStatus deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return HttpStatus.NO_CONTENT;
    }

    @GetMapping("/{articleId}/comments")
    public List<CommentDTO> getComments(@PathVariable Long articleId,
                                        @RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                        @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return commentService.getAllCommentsForArticle(articleId, PageRequest.of(offset, limit));
    }

    @PostMapping("/comments")
    public HttpStatus createComment(@RequestBody CommentDTO commentDTO) {
        commentService.createComment(commentDTO);
        return HttpStatus.CREATED;
    }
}
