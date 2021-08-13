package pl.czekaj.springsocial.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @NotBlank
    private String content;

    @NotNull
    private LocalDateTime timeCreated;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "postId",updatable = false,insertable = false)
    private List<Comment> comments;
}
