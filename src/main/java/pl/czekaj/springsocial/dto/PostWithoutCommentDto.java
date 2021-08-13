package pl.czekaj.springsocial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class PostWithoutCommentDto {
    private long id;
    private String content;
    private LocalDateTime timeCreated;
}
