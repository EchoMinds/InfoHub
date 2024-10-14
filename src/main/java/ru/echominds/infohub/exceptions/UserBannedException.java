package ru.echominds.infohub.exceptions;

import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class UserBannedException extends ApiException {
    public UserBannedException() {
        super("User banned", HttpStatus.FORBIDDEN);
    }

    public UserBannedException(OffsetDateTime banTime, String adminName, String reason) {
        super(String.format(
                "You are banned until %s by admin: \"%s\" for the reason: \"%s\"",
                banTime.format(DateTimeFormatter.ofPattern("HH:mm:ss yyyy-MM-dd")),
                adminName,
                reason
        ), HttpStatus.FORBIDDEN);

    }

    public UserBannedException(String adminName, String reason) {
        super(String.format(
                "You are permanently banned by admin: \"%s\" for the reason: \"%s\"",
                adminName,
                reason
        ), HttpStatus.FORBIDDEN);
    }
}
