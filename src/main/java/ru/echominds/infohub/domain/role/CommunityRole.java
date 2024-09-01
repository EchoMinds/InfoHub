package ru.echominds.infohub.domain.role;

import jakarta.persistence.*;
import lombok.Data;
import ru.echominds.infohub.domain.Community;
import ru.echominds.infohub.domain.User;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "community_role")
@Data
public class CommunityRole {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column()
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    @Enumerated(EnumType.STRING)
    private CommunityRolesType role;

}
