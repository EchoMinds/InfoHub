package ru.echominds.infohub.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "avatar_url")
    private String avatar;

    @Column(name = "email")
    private String email;

    // рейтинги которые человек ставил на статьи
    @OneToMany(mappedBy = "user")
    private List<Rating> ArticleRating;

    // ElementCollection хрень что бы храниц многа ролей и шоб пользоваться енумом, а не делать отдельны класс
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<Role> roles = new HashSet<>();

//    соц кредит будет как типа рейтинг для профиля
//    private Long socialCredit;

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
