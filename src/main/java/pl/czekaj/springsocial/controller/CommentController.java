package pl.czekaj.springsocial.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.czekaj.springsocial.dto.CommentDto;
import pl.czekaj.springsocial.model.Comment;
import pl.czekaj.springsocial.service.CommentService;

import java.util.List;

import static pl.czekaj.springsocial.controller.controllerHelper.PageAndSortDirectionHelper.getPageNumberGreaterThenZeroAndNotNull;
import static pl.czekaj.springsocial.controller.controllerHelper.PageAndSortDirectionHelper.getSortDirectionNotNullAndDESC;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/comments")
    public ResponseEntity<List<CommentDto>> getComments(@RequestParam(required = false) Integer page, Sort.Direction sort){
        int pageNumber = getPageNumberGreaterThenZeroAndNotNull(page);
        Sort.Direction sortDirection = getSortDirectionNotNullAndDESC(sort);
        List<CommentDto> comments = commentService.getComments(pageNumber,sortDirection);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsInPost(@PathVariable Long id,@RequestParam(required = false) Integer page, Sort.Direction sort){
        int pageNumber = getPageNumberGreaterThenZeroAndNotNull(page);
        Sort.Direction sortDirection = getSortDirectionNotNullAndDESC(sort);
        List<CommentDto> comments = commentService.getCommentsFromPost(id,pageNumber,sortDirection);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/posts/{id}/comments/{c_id}")
    public ResponseEntity<CommentDto> getSingleComment(@PathVariable Long id, @PathVariable Long c_id){
        return new ResponseEntity<>(commentService.getSingleComment(id,c_id),HttpStatus.OK);
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
        commentService.deleteComment(c_id);
    }
}
