package pl.czekaj.springsocial.dto.mapper;

import pl.czekaj.springsocial.dto.PostWithoutCommentDto;
import pl.czekaj.springsocial.model.Post;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PostWithoutCommentDtoMapper {

    private PostWithoutCommentDtoMapper(){
    }

    public static List<PostWithoutCommentDto> mapToPostWithoutCommentDtos(List<Post> posts){
        return posts.stream().map(PostWithoutCommentDtoMapper::mapToPostWithoutCommentDto).collect(Collectors.toList());
    }
    public static Set<PostWithoutCommentDto> mapToPostWithoutCommentDtos(Set<Post> posts){
        return posts.stream().map(PostWithoutCommentDtoMapper::mapToPostWithoutCommentDto).collect(Collectors.toSet());
    }

    public static PostWithoutCommentDto mapToPostWithoutCommentDtos(Post post){
        return mapToPostWithoutCommentDto(post);
    }

    private static PostWithoutCommentDto mapToPostWithoutCommentDto(Post post) {
        return PostWithoutCommentDto.builder()
                .id(post.getPostId())
                .content(post.getContent())
                .timeCreated(post.getTimeCreated())
                .build();
    }
}
