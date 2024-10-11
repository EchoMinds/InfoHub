package ru.echominds.infohub.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user", schema = "public")
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "avatar_url")
    private String avatar;

    private String email;

    // рейтинги которые человек ставил на статьи
    @OneToMany(mappedBy = "user")
    private List<Rating> ArticleRating;

    private Boolean is_banned;

    // ElementCollection хрень что бы храниц многа ролей и шоб пользоваться енумом, а не делать отдельны класс
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Article> Articles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public User(String name, String avatar,
                String email, List<Rating> ArticleRating,
                Set<Role> roles) {
        this.name = name;
        this.avatar = avatar;
        this.email = email;
        this.ArticleRating = ArticleRating;
        this.roles = roles;
    }
}
