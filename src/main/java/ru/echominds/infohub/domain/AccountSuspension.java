package ru.echominds.infohub.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "AccountSuspensions")
@Data
public class AccountSuspension extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private OffsetDateTime banTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    private User admin;

    private String reasonBan;

    private Boolean isBanned;

    private Boolean isPermanentBan;
}
