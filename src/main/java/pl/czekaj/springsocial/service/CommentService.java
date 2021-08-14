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
    public List<CommentDto> getCommentsFromPost(Long id, int page, Sort.Direction sort) {
        List<Comment> comments = commentRepository.findAllByPostId(id,PageRequest.of(page, PAGE_SIZE, Sort.by(sort, "timeCreated")));
        return CommentDtoMapper.mapToCommentDtos(comments);
    }

    @Cacheable(cacheNames = "getSingleComment")
    public CommentDto getSingleComment(Long p_id,Long c_id){
        Comment comment = commentRepository.findByPostIdAndCommentId(p_id,c_id);
        return CommentDtoMapper.mapToCommentDtos(comment);
    }

    @Cacheable(cacheNames = "addComment")
    @Transactional
    public CommentDto addComment(Comment comment,Long id){
        comment.setPostId(id);
        commentRepository.save(comment);
        return CommentDtoMapper.mapToCommentDtos(comment);
    }

    @Transactional
    @CachePut(cacheNames = "editComment", key= "#result.commentId")
    public CommentDto editComment(Comment comment,Long id){
        Comment editedComment = commentRepository.findByPostIdAndCommentId(id, comment.getCommentId());
        editedComment.setContent(comment.getContent());
        commentRepository.save(comment);
        return CommentDtoMapper.mapToCommentDtos(comment);
    }

    @Transactional
    @CachePut(cacheNames = "editSingleComment", key= "#result.commentId")
    public CommentDto editSingleComment(Comment comment,Long id,Long c_id){
        comment.setContent(comment.getContent());
        comment.setPostId(id);
        comment.setCommentId(c_id);
        commentRepository.save(comment);
        return CommentDtoMapper.mapToCommentDtos(comment);
    }

    @CacheEvict(cacheNames = "deleteComment")
    public void deleteComment(Long p_id,Long c_id){
        commentRepository.deleteByCommentIdAndPostId(c_id,p_id);
    }
}
