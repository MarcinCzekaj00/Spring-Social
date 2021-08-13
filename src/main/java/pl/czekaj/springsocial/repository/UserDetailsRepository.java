package pl.czekaj.springsocial.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.czekaj.springsocial.model.UserDetails;

import java.util.List;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails,Long> {

    @Query("select d From UserDetails d")
    List<UserDetails> findAllDetails(Pageable page);

    @Modifying
    @Query("delete from UserDetails ud where ud.userDetailsId = :id")
    void removeDetails(Long id);


}
