package pl.czekaj.springsocial.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.czekaj.springsocial.dto.UserDto;
import pl.czekaj.springsocial.dto.mapper.UserDtoMapper;
import pl.czekaj.springsocial.enums.Role;
import pl.czekaj.springsocial.model.User;
import pl.czekaj.springsocial.repository.UserRepository;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private static final int PAGE_SIZE = 10;

    public void addWithDefaultRole(User user){
        user.setRole(Role.user);
        String passwordHash = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordHash);
        try{
            userRepository.save(user);
        }catch (ConstraintViolationException e){
            Set<ConstraintViolation<?>> errors = e.getConstraintViolations();
            errors.forEach(err -> System.err.println(
                    err.getPropertyPath() + " " +
                            err.getInvalidValue() + " " +
                            err.getMessage()));
            throw e;
        }
    }

    @Cacheable(cacheNames = "getUsers")
    public List<UserDto> getUsers(int page, Sort.Direction sort) {
        List <User> users = userRepository.findAllUsers(PageRequest.of(page, PAGE_SIZE, Sort.by(sort, "userId")));
        return UserDtoMapper.mapToUserDtos(users);
    }


    @Cacheable(cacheNames = "getSingleUser")
    public UserDto getSingleUser(Long id){
        User user = userRepository.findById(id).orElseThrow();
        return UserDtoMapper.mapToUserDtos(user);
    }

    public User getUserInfo(Long id){
        return userRepository.findById(id).orElseThrow();
    }

    public User addUser(User user){
        return userRepository.save(user);
    }

    @Transactional
    @CachePut(cacheNames = "editSingleUser", key= "#result.userId")
    public UserDto editSingleUser(User user, Long id){
        user.setUserId(id);
        userRepository.save(user);
        return UserDtoMapper.mapToUserDtos(user);
    }

    @Transactional
    @CachePut(cacheNames = "editUser", key= "#result.userId")
    public UserDto editUser(User user){
        User editedUser = userRepository.findById(user.getUserId()).orElseThrow();
        editedUser.setRole(user.getRole());
        editedUser.setEmail(user.getEmail());
        editedUser.setFirstName(user.getFirstName());
        editedUser.setLastName(user.getLastName());
        userRepository.save(editedUser);
        return UserDtoMapper.mapToUserDtos(editedUser);
    }

    @CacheEvict(cacheNames = "deleteUser")
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
