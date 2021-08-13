package pl.czekaj.springsocial.dto.mapper;

import pl.czekaj.springsocial.dto.CommentDto;
import pl.czekaj.springsocial.model.Comment;

import java.util.List;
import java.util.stream.Collectors;

public class CommentDtoMapper {

    private CommentDtoMapper(){
    }

    public static List<CommentDto> mapToCommentDtos(List<Comment> comments){
        return comments.stream().map(CommentDtoMapper::mapToCommentDto).collect(Collectors.toList());
    }

    public static CommentDto mapToCommentDtos(Comment comment){
        return mapToCommentDto(comment);
    }

    private static CommentDto mapToCommentDto(Comment comment) {
        return CommentDto.builder()
                .commentId(comment.getCommentId())
                .postId(comment.getPostId())
                .content(comment.getContent())
                .timeCreated(comment.getTimeCreated())
                .build();
    }
}
