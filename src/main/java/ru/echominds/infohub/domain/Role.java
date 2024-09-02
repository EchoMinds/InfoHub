package ru.echominds.infohub.domain;

import java.util.Set;

//система ролей может переделываться
public enum Role {
    HEAD_ADMINISTRATOR(Set.of("ROLE_USER", "ROLE_ADMINISTRATOR", "ROLE_HEAD_ADMINISTRATOR")),
    ADMINISTRATOR(Set.of("ROLE_USER", "ROLE_ADMINISTRATOR")),
    USER(Set.of("ROLE_USER"));

    private final Set<String> authorities;

    Role(Set<String> authorities) {
        this.authorities = authorities;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }
}
