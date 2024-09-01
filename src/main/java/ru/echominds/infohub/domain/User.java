package ru.echominds.infohub.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;


@Entity
@Data
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String avatar;

    private String name;

    private String email;

    // рейтинги которые человек ставил на статьи
    @OneToMany(mappedBy = "user")
    private List<Rating> ratings;


    //роли еще над будет подключить

//    соц кредит будет как типа рейтинг для профиля
//    private Long socialCredit;
}
