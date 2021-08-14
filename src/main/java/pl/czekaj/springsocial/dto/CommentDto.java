package pl.czekaj.springsocial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import pl.czekaj.springsocial.model.Comment;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
public class CommentDto extends RepresentationModel<PostDto> {

    private Long commentId;
    private Long postId;
    private String content;
    private LocalDateTime timeCreated;

}
