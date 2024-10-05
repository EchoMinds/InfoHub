package ru.echominds.infohub.exceptions;

import org.springframework.http.HttpStatus;

public class NoPermissionException extends ApiException {
    public NoPermissionException() {
        super("No permission", HttpStatus.FORBIDDEN);
    }
}
