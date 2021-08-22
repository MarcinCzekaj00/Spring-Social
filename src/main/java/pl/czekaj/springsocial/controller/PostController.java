package pl.czekaj.springsocial.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.czekaj.springsocial.controller.controllerHelper.PageHelper;
import pl.czekaj.springsocial.controller.controllerHelper.SortDirectionHelper;
import pl.czekaj.springsocial.dto.CommentDto;
import pl.czekaj.springsocial.dto.PostDto;
import pl.czekaj.springsocial.dto.PostWithoutCommentDto;
import pl.czekaj.springsocial.model.Comment;
import pl.czekaj.springsocial.model.Post;
import pl.czekaj.springsocial.service.CommentService;
import pl.czekaj.springsocial.service.PostService;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<CollectionModel<PostWithoutCommentDto>> getPosts(@RequestParam(required = false) Integer page,
                                                                           @RequestParam(required = false, defaultValue = "DESC") Sort.Direction sort){
        int pageNumber = PageHelper.getPageNumberGreaterThenZeroAndNotNull(page);
        Sort.Direction sortDirection = SortDirectionHelper.getSortDirection(sort);
        List<PostWithoutCommentDto> posts = postService.getPosts(pageNumber,sortDirection);
        for (PostWithoutCommentDto post: posts){
            post.add(linkTo(PostController.class).slash(post.getId()).withRel("Post with comments"));
        }
        Link link = linkTo(PostController.class).withSelfRel();
        CollectionModel<PostWithoutCommentDto> postResource = new CollectionModel<>(posts,link);
        return new ResponseEntity<>(postResource, HttpStatus.OK);
    }

    @GetMapping("/comments")
    public ResponseEntity<CollectionModel<PostDto>> getPostsWithComments(@RequestParam(required = false) Integer page,
                                                                         @RequestParam(required = false, defaultValue = "DESC") Sort.Direction sort){
        int pageNumber = PageHelper.getPageNumberGreaterThenZeroAndNotNull(page);
        Sort.Direction sortDirection = SortDirectionHelper.getSortDirection(sort);
        List<PostDto> posts = postService.getPostsWithComments(pageNumber, sortDirection);
        for (PostDto post: posts){
            Long postId = post.getId();
            post.add(linkTo(PostController.class).slash(post.getId()).withSelfRel());
            List <CommentDto> comments = commentService.getCommentsFromPost(postId,pageNumber,sortDirection);
            for (CommentDto comment:comments){
                post.add(linkTo(methodOn(CommentController.class).getSingleComment(postId,comment.getCommentId())).withRel("Comments"));
            }
            post.add(linkTo(methodOn(CommentController.class).getCommentsInPost(postId,pageNumber,sortDirection)).withRel("All comments from this post"));
        }
        Link link = linkTo(methodOn(PostController.class).getPostsWithComments(pageNumber,sortDirection)).withSelfRel();
        CollectionModel<PostDto> postResource = new CollectionModel<>(posts,link);
        return new ResponseEntity<>(postResource, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PostDto>> getSinglePost(@PathVariable Long id){
        PostDto post = postService.getSinglePost(id);
        List<Comment> comments = post.getComments();
        post.add(linkTo(PostController.class).slash(id).withSelfRel());
        for(Comment comment: comments){
            post.add(linkTo(methodOn(CommentController.class).getSingleComment(id,comment.getCommentId())).withRel("Comments"));
        }
        Link link = linkTo(PostController.class).withRel("All Posts");
        EntityModel<PostDto> postDtoResource = new EntityModel<>(post,link);
        return new ResponseEntity<>(postDtoResource, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostWithoutCommentDto> addPost(@RequestBody Post post){
        return new ResponseEntity<>(postService.addPost(post), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<PostDto> editPost(@RequestBody Post post){
        return new ResponseEntity<>(postService.editPost(post), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> editSinglePost(@RequestBody Post post, @PathVariable Long id){
        return new ResponseEntity<>(postService.editSinglePost(post,id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id){
       postService.deletePost(id);
    }

}
