package pl.czekaj.springsocial.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.czekaj.springsocial.model.User;
import pl.czekaj.springsocial.model.UserDetails;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

    @Query("select d From User d")
    List<User> findAllUsers(Pageable page);

    @Modifying
    @Query("update User u set u.details = null where u.userId = :id")
    void removeDetails(Long id);
}
