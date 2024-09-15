package ru.echominds.infohub.dtos;


public record UserDTO(
        Long id,
        String name,
        String email,
        String avatar
) {
}
