package pl.czekaj.springsocial.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.czekaj.springsocial.dto.PostWithoutCommentDto;
import pl.czekaj.springsocial.dto.PostDto;
import pl.czekaj.springsocial.dto.mapper.PostDtoMapper;
import pl.czekaj.springsocial.model.Post;
import pl.czekaj.springsocial.service.PostService;

import java.util.List;

import static pl.czekaj.springsocial.controller.controllerHelper.PageAndSortDirectionHelper.getPageNumberGreaterThenZeroAndNotNull;
import static pl.czekaj.springsocial.controller.controllerHelper.PageAndSortDirectionHelper.getSortDirectionNotNullAndDESC;


@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostWithoutCommentDto>> getPosts(@RequestParam(required = false) Integer page, Sort.Direction sort){
        int pageNumber = getPageNumberGreaterThenZeroAndNotNull(page);
        Sort.Direction sortDirection = getSortDirectionNotNullAndDESC(sort);
        List<PostWithoutCommentDto> posts = postService.getPosts(pageNumber,sortDirection);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/comments")
    public ResponseEntity<List<PostDto>> getPostsWithComments(@RequestParam(required = false) Integer page, Sort.Direction sort){
        int pageNumber = getPageNumberGreaterThenZeroAndNotNull(page);
        Sort.Direction sortDirection = getSortDirectionNotNullAndDESC(sort);
        List<PostDto> posts = postService.getPostsWithComments(pageNumber, sortDirection);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getSinglePost(@PathVariable Long id){
        return new ResponseEntity<>(postService.getSinglePost(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostWithoutCommentDto> addPost(@RequestBody Post post){
        return new ResponseEntity<>(postService.addPost(post), HttpStatus.OK);
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
