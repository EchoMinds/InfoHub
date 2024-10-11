package ru.echominds.infohub.exceptions;

import org.springframework.http.HttpStatus;

public class UserBannedException extends ApiException {
    public UserBannedException() {
        super("User banned", HttpStatus.FORBIDDEN);
    }
}
