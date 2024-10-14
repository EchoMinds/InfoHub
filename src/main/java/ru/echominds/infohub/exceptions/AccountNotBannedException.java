package ru.echominds.infohub.exceptions;

import org.springframework.http.HttpStatus;

public class AccountNotBannedException extends ApiException{
    public AccountNotBannedException() {
        super("User not banned", HttpStatus.NOT_FOUND);
    }
}
