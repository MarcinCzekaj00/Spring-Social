package pl.czekaj.springsocial.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.czekaj.springsocial.dto.CommentDto;
import pl.czekaj.springsocial.dto.mapper.CommentDtoMapper;
import pl.czekaj.springsocial.model.Comment;
import pl.czekaj.springsocial.repository.CommentRepository;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private static final int PAGE_SIZE = 50;
    private final CommentRepository commentRepository;

    @Cacheable(cacheNames = "getComments")
    public List<CommentDto> getComments(int page, Sort.Direction sort) {
        List<Comment> comments = commentRepository.findAllComments(PageRequest.of(page, PAGE_SIZE, Sort.by(sort, "timeCreated")));
        return CommentDtoMapper.mapToCommentDtos(comments);
    }

    @Cacheable(cacheNames = "getCommentsFromPost")
    public List<CommentDto> getCommentsFromPost(Long postId, int page, Sort.Direction sort) {
        List<Comment> comments = commentRepository.findAllByPostId(postId,PageRequest.of(page, PAGE_SIZE, Sort.by(sort, "timeCreated")));
        return CommentDtoMapper.mapToCommentDtos(comments);
    }

    @Cacheable(cacheNames = "getSingleComment")
    public CommentDto getSingleComment(Long postId,Long commentId){
        Comment comment = commentRepository.findByPostIdAndCommentId(postId,commentId);
        return CommentDtoMapper.mapToCommentDtos(comment);
    }

    @Cacheable(cacheNames = "addComment")
    @Transactional
    public CommentDto addComment(Comment comment,Long postId){
        comment.setPostId(postId);
        commentRepository.save(comment);
        return CommentDtoMapper.mapToCommentDtos(comment);
    }

    @Transactional
    @CachePut(cacheNames = "editComment", key= "#result.commentId")
    public CommentDto editComment(Comment comment,Long postId){
        Comment editedComment = commentRepository.findByPostIdAndCommentId(postId, comment.getCommentId());
        editedComment.setContent(comment.getContent());
        commentRepository.save(comment);
        return CommentDtoMapper.mapToCommentDtos(comment);
    }

    @Transactional
    @CachePut(cacheNames = "editSingleComment", key= "#result.commentId")
    public CommentDto editSingleComment(Comment comment,Long postId,Long commentId){
        comment.setContent(comment.getContent());
        comment.setPostId(postId);
        comment.setCommentId(commentId);
        commentRepository.save(comment);
        return CommentDtoMapper.mapToCommentDtos(comment);
    }

    @CacheEvict(cacheNames = "deleteComment")
    public void deleteComment(Long postId,Long commentId){
        commentRepository.deleteByCommentIdAndPostId(commentId,postId);
    }
}
