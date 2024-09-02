package ru.echominds.infohub.dtos;

import java.util.Set;

public record UserDTO(
        String name,
        String email,
        String avatar,
        Set<String> roles
) {
}
