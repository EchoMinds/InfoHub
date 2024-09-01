package ru.echominds.infohub.domain.role;

import jakarta.persistence.*;
import lombok.Data;
import ru.echominds.infohub.domain.Article;
import ru.echominds.infohub.domain.User;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table(name = "website_role", schema = "public")
public class WebSiteRole {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @Enumerated(STRING)
    private WebSiteRolesType type;
}
