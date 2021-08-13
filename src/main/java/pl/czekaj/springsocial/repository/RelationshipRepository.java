package pl.czekaj.springsocial.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.czekaj.springsocial.model.Relationship;

import java.util.List;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship,Long> {

    @Query("select f.toUserId from Relationship f left join User u on f.toUserId = u.userId where u.userId is not null and f.fromUserId = :f_user")
    List<Long> findAllFriends (Long f_user, Pageable page);

    @Query("select f from Relationship f where f.fromUserId = :f_user and f.toUserId = :t_user")
    Relationship findRelationship (Long f_user, Long t_user);

    @Query("select f from Relationship f where f.fromUserId = :t_user and f.toUserId = :f_user")
    Relationship findRelationshipFeedback (Long f_user, Long t_user);

}
