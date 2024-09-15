package ru.echominds.infohub.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "Article")
public class Article extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long articleId;

    //author article
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "views")
    private Long views;

    @OneToMany(mappedBy = "article")
    private List<Rating> articleRating;

    @OneToMany(mappedBy = "article")
    private List<Comment> comments;

    //next tags

}
