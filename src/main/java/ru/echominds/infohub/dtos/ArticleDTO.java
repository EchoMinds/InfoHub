package ru.echominds.infohub.dtos;

public record ArticleDTO(
        UserDTO userDTO,
        String title,
        String text,
        Long views,
        Long articleRating
) {
}
