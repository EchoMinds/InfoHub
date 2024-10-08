package ru.echominds.infohub.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApiException {
    public UnauthorizedException() {
        super("No authorization", HttpStatus.FORBIDDEN);
    }
}
