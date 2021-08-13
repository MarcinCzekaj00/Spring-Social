package pl.czekaj.springsocial.dto.mapper;

import pl.czekaj.springsocial.dto.PostDto;
import pl.czekaj.springsocial.model.Post;

import java.util.List;
import java.util.stream.Collectors;

public class PostDtoMapper {

    private PostDtoMapper(){
    }

    public static List<PostDto> mapToPostDtos(List<Post> posts){
        return posts.stream().map(PostDtoMapper::mapToPostDto).collect(Collectors.toList());
    }

    public static PostDto mapToPostDtos(Post post){
        return mapToPostDto(post);
    }

    private static PostDto mapToPostDto(Post post) {
        return PostDto.builder()
                .id(post.getPostId())
                .content(post.getContent())
                .timeCreated(post.getTimeCreated())
                .comments(post.getComments())
                .build();
    }
}
