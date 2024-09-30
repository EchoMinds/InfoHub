package ru.echominds.infohub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // users
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(UserNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                "User not found",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoPermissionException.class)
    public ResponseEntity<ErrorResponse> handleException(NoPermissionException e) {
        ErrorResponse response = new ErrorResponse(
                "No permission",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleException(UnauthorizedException e) {
        ErrorResponse response = new ErrorResponse(
                "No authorization",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    // article
    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ArticleNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                "Article not found",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    //comment
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(CommentNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                "Comment not found",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ArticleNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                "Article not found",
                System.currentTimeMillis()
        );

        //return 404
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(UserNotAuthorArticle.class)
    public ResponseEntity<ErrorResponse> handleException(UserNotAuthorArticle e) {
        ErrorResponse response = new ErrorResponse(
                "Article not found",
                System.currentTimeMillis()
        );

        //return 404
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
