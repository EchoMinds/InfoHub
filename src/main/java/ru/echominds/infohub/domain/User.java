package ru.echominds.infohub.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user", schema = "public")
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "avatar_url")
    private String avatar;

    @Column(name = "email")
    private String email;

    // рейтинги которые человек ставил на статьи
    // !! НЕ ЕГО РЕЙТИНГ !!
    @OneToMany(mappedBy = "user")
    private List<Rating> ratings;


//    соц кредит будет как типа рейтинг для профиля
//    private Long socialCredit;
}
