package pl.czekaj.springsocial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class PostWithoutCommentDto extends RepresentationModel<PostWithoutCommentDto> {
    private long id;
    private String content;
    private LocalDateTime timeCreated;
}
