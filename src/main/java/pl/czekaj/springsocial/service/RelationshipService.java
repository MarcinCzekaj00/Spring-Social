package pl.czekaj.springsocial.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.czekaj.springsocial.model.Relationship;
import pl.czekaj.springsocial.model.User;
import pl.czekaj.springsocial.repository.RelationshipRepository;
import pl.czekaj.springsocial.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class RelationshipService {

    private static final int PAGE_SIZE = 20;
    private RelationshipRepository relationshipRepository;
    private UserRepository userRepository;

    @Cacheable(cacheNames = "getFriends")
    public List<Long> getFriends(Long fromUserId, int page, Sort.Direction sort) {
        return relationshipRepository.findAllFriends(fromUserId,PageRequest.of(page, PAGE_SIZE, Sort.by(sort, "relationshipId")));
    }

    @Cacheable(cacheNames = "addFriend")
    @Transactional
    public User addFriend(Long id,User userToAdd){
        User checkUserToAdd = userRepository.findById(userToAdd.getUserId()).orElseThrow();
        User user = userRepository.findById(id).orElseThrow();
        Relationship relationship = getRelationship(user, userToAdd);
        Relationship relationshipFeedBack = getRelationshipFeedback(user, userToAdd);

        if(checkUserToAdd.equals(userToAdd) && relationshipRepository.findRelationship(id,checkUserToAdd.getUserId()) == null) {

            Set<Relationship> friends = user.getFriends();
            friends.add(relationship);

            Set<Relationship> friends2 = userToAdd.getFriends();
            friends2.add(relationshipFeedBack);

            relationshipRepository.save(relationship);
            relationshipRepository.save(relationshipFeedBack);
            userRepository.save(user);
            userRepository.save(userToAdd);

            return userToAdd;
        }
        throw new IllegalArgumentException();
    }

    @CacheEvict(cacheNames = "deleteFriend")
    public void deleteFriend(Long id, User userToDelete){
        User user = userRepository.findById(id).orElseThrow();
        Relationship relationship = relationshipRepository.findRelationship(user.getUserId(),userToDelete.getUserId());
        Relationship relationshipFeedBack = relationshipRepository.findRelationshipFeedback(user.getUserId(),userToDelete.getUserId());
        relationshipRepository.delete(relationship);
        relationshipRepository.delete(relationshipFeedBack);
    }

    private Relationship getRelationship(User user ,User secondUser){
        Relationship relationship = new Relationship();
        relationship.setFromUserId(user.getUserId());
        relationship.setToUserId(secondUser.getUserId());
        return relationship;
    }

    private Relationship getRelationshipFeedback(User user, User secondUser){
        Relationship relationship = new Relationship();
        relationship.setFromUserId(secondUser.getUserId());
        relationship.setToUserId(user.getUserId());
        return relationship;
    }
}
