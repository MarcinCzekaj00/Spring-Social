package pl.czekaj.springsocial.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.czekaj.springsocial.dto.CommentDto;
import pl.czekaj.springsocial.dto.PostDto;
import pl.czekaj.springsocial.dto.PostWithoutCommentDto;
import pl.czekaj.springsocial.model.Comment;
import pl.czekaj.springsocial.service.CommentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static pl.czekaj.springsocial.controller.controllerHelper.PageAndSortDirectionHelper.getPageNumberGreaterThenZeroAndNotNull;
import static pl.czekaj.springsocial.controller.controllerHelper.PageAndSortDirectionHelper.getSortDirectionNotNullAndDESC;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/comments")
    public ResponseEntity<CollectionModel<CommentDto>> getComments(@RequestParam(required = false) Integer page, Sort.Direction sort){
        int pageNumber = getPageNumberGreaterThenZeroAndNotNull(page);
        Sort.Direction sortDirection = getSortDirectionNotNullAndDESC(sort);
        List<CommentDto> comments = commentService.getComments(pageNumber,sortDirection);
        for (CommentDto comment: comments){
            comment.add(linkTo(methodOn(CommentController.class).getCommentsInPost(comment.getPostId(),pageNumber,sortDirection))
                                                                .slash(comment.getCommentId()).withSelfRel());
        }
        Link link = linkTo(methodOn(CommentController.class).getComments(pageNumber,sortDirection)).withSelfRel();
        CollectionModel<CommentDto> commentResource = new CollectionModel<>(comments,link);
        return new ResponseEntity<>(commentResource, HttpStatus.OK);
    }

    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<CollectionModel<CommentDto>> getCommentsInPost(@PathVariable Long id, @RequestParam(required = false) Integer page, Sort.Direction sort){
        int pageNumber = getPageNumberGreaterThenZeroAndNotNull(page);
        Sort.Direction sortDirection = getSortDirectionNotNullAndDESC(sort);
        List<CommentDto> comments = commentService.getCommentsFromPost(id,pageNumber,sortDirection);
        for (CommentDto comment: comments){
            comment.add(linkTo(methodOn(CommentController.class).getSingleComment(id,comment.getCommentId())).withRel("Comment"));
        }
        Link link = linkTo(methodOn(CommentController.class).getCommentsInPost(id,pageNumber,sortDirection)).withSelfRel();
        CollectionModel<CommentDto> commentResource = new CollectionModel<>(comments,link);
        return new ResponseEntity<>(commentResource, HttpStatus.OK);
    }

    @GetMapping("/posts/{id}/comments/{c_id}")
    public ResponseEntity<EntityModel<CommentDto>> getSingleComment(@PathVariable Long id, @PathVariable Long c_id){
        CommentDto comment = commentService.getSingleComment(id,c_id);
        comment.add(linkTo(methodOn(PostController.class).getSinglePost(comment.getPostId())).withRel("Post"));
        Link link = linkTo(methodOn(CommentController.class).getSingleComment(id,comment.getCommentId())).withSelfRel();
        EntityModel<CommentDto> commentResource = new EntityModel<>(comment,link);
        return new ResponseEntity<>(commentResource,HttpStatus.OK);
    }

    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<CommentDto> addComment(@RequestBody Comment comment, @PathVariable Long id){
        return new ResponseEntity<>(commentService.addComment(comment,id), HttpStatus.OK);
    }

    @PutMapping("/posts/{id}/comments")
    public ResponseEntity<CommentDto> editComment(@RequestBody Comment comment,@PathVariable Long id){
        return new ResponseEntity<>(commentService.editComment(comment,id), HttpStatus.OK);
    }

    @PutMapping("/posts/{id}/comments/{c_id}")
    public ResponseEntity<CommentDto> editSingleComment(@RequestBody Comment comment,@PathVariable Long id,@PathVariable Long c_id){
        return new ResponseEntity<>(commentService.editSingleComment(comment,id,c_id), HttpStatus.OK);
    }

    @DeleteMapping("/posts/{id}/comments/{c_id}")
    public void deletePost(@PathVariable Long id,@PathVariable Long c_id){
        commentService.deleteComment(id,c_id);
    }
}
