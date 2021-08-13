package pl.czekaj.springsocial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.czekaj.springsocial.model.Comment;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
public class PostDto {
    private long id;
    private String content;
    private LocalDateTime timeCreated;
    private List<Comment> comments;
}