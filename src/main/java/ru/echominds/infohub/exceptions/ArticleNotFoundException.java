package ru.echominds.infohub.exceptions;

import org.springframework.http.HttpStatus;

public class ArticleNotFoundException extends ApiException {
    public ArticleNotFoundException() {
        super("Article not found", HttpStatus.NOT_FOUND);
    }
}
