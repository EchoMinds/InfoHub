package ru.echominds.infohub.domain;

import jakarta.persistence.*;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class Community {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column()
    private Long id;

//    private List<Article> articles;


}
