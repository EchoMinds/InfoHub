package ru.echominds.infohub.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Data
public class Auditable {

    @CreationTimestamp
    private OffsetDateTime createdAt;
    @UpdateTimestamp
    private OffsetDateTime updatedAt;
}
