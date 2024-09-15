package ru.echominds.infohub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(UserNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                "User not found",
                System.currentTimeMillis()
        );

        //return 404
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
}
