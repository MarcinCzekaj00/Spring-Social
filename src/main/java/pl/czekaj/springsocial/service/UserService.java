package pl.czekaj.springsocial.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import pl.czekaj.springsocial.dto.UserDto;
import pl.czekaj.springsocial.dto.mapper.UserDtoMapper;
import pl.czekaj.springsocial.enums.Role;
import pl.czekaj.springsocial.model.User;
import pl.czekaj.springsocial.repository.UserRepository;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsManager userDetailsManager;
    private static final int PAGE_SIZE = 50;

    public void addWithDefaultRole(User user){
        user.setRole(Role.user);
        String passwordHash = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordHash);
        try{
            userRepository.save(user);
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            org.springframework.security.core.userdetails.User userToAdd = new org.springframework.security.core.userdetails.User(
                                                                                       user.getEmail(), passwordHash,authorities);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), passwordHash, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            userDetailsManager.createUser(userToAdd);
        } catch (ConstraintViolationException e){
            Set<ConstraintViolation<?>> errors = e.getConstraintViolations();
            errors.forEach(err -> System.err.println(
                    err.getPropertyPath() + " " +
                            err.getInvalidValue() + " " +
                            err.getMessage()));
            throw e;
        }
    }

    public List<UserDto> getUsers(int page, Sort.Direction sort) {
        List <User> users = userRepository.findAllUsers(PageRequest.of(page, PAGE_SIZE, Sort.by(sort, "userId")));
        return UserDtoMapper.mapToUserDtos(users);
    }


    public UserDto getSingleUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow();
        return UserDtoMapper.mapToUserDtos(user);
    }

    public User getUserInfo(Long userId){
        return userRepository.findById(userId).orElseThrow();
    }

    public User addUser(User user){
        return userRepository.save(user);
    }

    @Transactional
    public UserDto editSingleUser(User user, Long userId){
        user.setUserId(userId);
        userRepository.save(user);
        return UserDtoMapper.mapToUserDtos(user);
    }

    @Transactional
    public UserDto editUser(User user){
        User editedUser = userRepository.findById(user.getUserId()).orElseThrow();
        editedUser.setRole(user.getRole());
        editedUser.setEmail(user.getEmail());
        editedUser.setFirstName(user.getFirstName());
        editedUser.setLastName(user.getLastName());
        userRepository.save(editedUser);
        return UserDtoMapper.mapToUserDtos(editedUser);
    }

    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }
}
