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
import pl.czekaj.springsocial.model.Comment;
import pl.czekaj.springsocial.service.CommentService;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/comments")
    public ResponseEntity<CollectionModel<CommentDto>> getComments(@RequestParam(required = false) Integer page,
                                                                   @RequestParam(required = false, defaultValue = "DESC") Sort.Direction sort){
        int pageNumber = PageHelper.getPageNumberGreaterThenZeroAndNotNull(page);
        Sort.Direction sortDirection = SortDirectionHelper.getSortDirection(sort);
        List<CommentDto> comments = commentService.getComments(pageNumber,sortDirection);
        for (CommentDto comment: comments){
            comment.add(linkTo(methodOn(CommentController.class).getCommentsInPost(comment.getPostId(),pageNumber,sortDirection))
                                                                .slash(comment.getCommentId()).withSelfRel());
        }
        Link link = linkTo(methodOn(CommentController.class).getComments(pageNumber,sortDirection)).withSelfRel();
        CollectionModel<CommentDto> commentResource = new CollectionModel<>(comments,link);
        return new ResponseEntity<>(commentResource, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<CollectionModel<CommentDto>> getCommentsInPost(@PathVariable Long postId, @RequestParam(required = false) Integer page,
                                                                         @RequestParam(required = false, defaultValue = "DESC") Sort.Direction sort){
        int pageNumber = PageHelper.getPageNumberGreaterThenZeroAndNotNull(page);
        Sort.Direction sortDirection = SortDirectionHelper.getSortDirection(sort);
        List<CommentDto> comments = commentService.getCommentsFromPost(postId,pageNumber,sortDirection);
        for (CommentDto comment: comments){
            comment.add(linkTo(methodOn(CommentController.class).getSingleComment(postId,comment.getCommentId())).withRel("Comment"));
        }
        Link link = linkTo(methodOn(CommentController.class).getCommentsInPost(postId,pageNumber,sortDirection)).withSelfRel();
        CollectionModel<CommentDto> commentResource = new CollectionModel<>(comments,link);
        return new ResponseEntity<>(commentResource, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<EntityModel<CommentDto>> getSingleComment(@PathVariable Long postId, @PathVariable Long commentId){
        CommentDto comment = commentService.getSingleComment(postId,commentId);
        comment.add(linkTo(methodOn(PostController.class).getSinglePost(comment.getPostId())).withRel("Post"));
        Link link = linkTo(methodOn(CommentController.class).getSingleComment(postId,comment.getCommentId())).withSelfRel();
        EntityModel<CommentDto> commentResource = new EntityModel<>(comment,link);
        return new ResponseEntity<>(commentResource,HttpStatus.OK);
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> addComment(@RequestBody Comment comment, @PathVariable Long postId){
        return new ResponseEntity<>(commentService.addComment(comment,postId), HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> editComment(@RequestBody Comment comment,@PathVariable Long postId){
        return new ResponseEntity<>(commentService.editComment(comment,postId), HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> editSingleComment(@RequestBody Comment comment,@PathVariable Long postId,@PathVariable Long commentId){
        return new ResponseEntity<>(commentService.editSingleComment(comment,postId,commentId), HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public void deletePost(@PathVariable Long postId,@PathVariable Long commentId){
        commentService.deleteComment(postId,commentId);
    }
}
