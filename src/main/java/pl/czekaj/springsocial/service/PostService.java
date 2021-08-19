package pl.czekaj.springsocial.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.czekaj.springsocial.dto.PostDto;
import pl.czekaj.springsocial.dto.mapper.PostDtoMapper;
import pl.czekaj.springsocial.dto.PostWithoutCommentDto;
import pl.czekaj.springsocial.dto.mapper.PostWithoutCommentDtoMapper;
import pl.czekaj.springsocial.exception.postException.AddPostException;
import pl.czekaj.springsocial.exception.postException.PostNotFoundException;
import pl.czekaj.springsocial.model.Comment;
import pl.czekaj.springsocial.model.Post;
import pl.czekaj.springsocial.repository.CommentRepository;
import pl.czekaj.springsocial.repository.PostRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private static final int PAGE_SIZE = 50;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Cacheable(cacheNames = "getPosts")
    public List<PostWithoutCommentDto> getPosts(int page, Sort.Direction sort) {
        List<Post> posts = postRepository.findAllPosts(PageRequest.of(page, PAGE_SIZE, Sort.by(sort, "timeCreated")));
        if(posts.isEmpty()) throw new PostNotFoundException();
        return PostWithoutCommentDtoMapper.mapToPostWithoutCommentDtos(posts);
    }

    @Cacheable(cacheNames = "getSinglePost")
    public PostDto getSinglePost(Long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        return PostDtoMapper.mapToPostDtos(post);
    }

    @Cacheable(cacheNames = "getPostsWithComments")
    public List<PostDto> getPostsWithComments(int page, Sort.Direction sort){
        List<Post> posts = postRepository.findAllPosts(PageRequest.of(page, PAGE_SIZE, Sort.by(sort, "timeCreated")));
        if(posts.isEmpty()) throw new PostNotFoundException();

        List <PostDto> postDtos = PostDtoMapper.mapToPostDtos(posts);
        List<Long> ids = postDtos.stream()
                .map(PostDto::getId)
                .collect(Collectors.toList());
        List<Comment> comments = commentRepository.findAllByPostIdIn(ids);
        postDtos.forEach(post -> post.setComments(extractComments(comments,post.getId())));
        return postDtos;
    }


    private List<Comment> extractComments(List<Comment> comments,Long id){
        return comments.stream()
                .filter(comment -> comment.getPostId().equals(id))
                .collect(Collectors.toList());
    }

    public PostWithoutCommentDto addPost(Post post){
        try{
            postRepository.save(post);
        } catch(Throwable e){
            throw new AddPostException();
        }
        return PostWithoutCommentDtoMapper.mapToPostWithoutCommentDtos(post);
    }

    @CachePut(cacheNames = "editPost", key= "#result.id")
    @Transactional
    public PostDto editPost(Post post){
        Post editedPost = postRepository.findById(post.getPostId()).orElseThrow(PostNotFoundException::new);
        editedPost.setContent(post.getContent());
        postRepository.save(post);
        return PostDtoMapper.mapToPostDtos(post);
    }

    @CachePut(cacheNames = "editSinglePost", key= "#result.id")
    @Transactional
    public PostDto editSinglePost(Post post,Long id){
        postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        post.setPostId(id);
        postRepository.save(post);
        return PostDtoMapper.mapToPostDtos(post);
    }

    @CacheEvict(cacheNames = "deletePost")
    public void deletePost(Long id){
        postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        postRepository.deleteById(id);
    }

}
