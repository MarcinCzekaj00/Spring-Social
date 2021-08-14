package pl.czekaj.springsocial.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.czekaj.springsocial.model.Comment;
import pl.czekaj.springsocial.model.Post;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findAllByPostIdIn(List<Long> ids);

    @Query("select c From Comment c")
    List<Comment> findAllComments(Pageable page);

    @Query("select c From Comment c where c.postId=:id")
    List<Comment> findAllByPostId(Long id,Pageable page);

    Comment findByPostIdAndCommentId(Long p_id,Long c_id);

    void deleteByCommentIdAndPostId(Long p_id,Long c_id);

}
