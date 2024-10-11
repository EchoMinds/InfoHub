package ru.echominds.infohub.dtos;

import java.time.OffsetDateTime;
import java.util.Optional;

public record BanUserInformationDTO(
        Optional<OffsetDateTime> banTime,
        String reasonBan
) {
}
