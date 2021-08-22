package pl.czekaj.springsocial.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import pl.czekaj.springsocial.dto.UserDto;
import pl.czekaj.springsocial.enums.Role;
import pl.czekaj.springsocial.model.User;
import pl.czekaj.springsocial.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private final User u1 = new User(1L,"Tomasz","Cichocki",
                                "tomasz@op.pl","password",Role.user,
                                null,null,null);

    private final User u2 = new User(2L,"Pawel","Cichocki",
            "tomasz@op.pl","password",Role.user,
            null,null,null);

    private final User u3 = new User(3L,"Julian","Cichocki",
            "tomasz@op.pl","password",Role.user,
            null,null,null);

    @Test
    void shouldGetUsers() {
        //given
        given(userRepository.findAllUsers(PageRequest.of(1, 50, Sort.by(Sort.Direction.DESC, "userId"))))
                            .willReturn(prepareUserData());

        //when
        List<UserDto> result = userService.getUsers(1, Sort.Direction.DESC);

        //then
        assertUserFields(result.get(0));
        assertThat(result).hasSize(3);
        verify(userRepository,times(1)).
                                        findAllUsers(PageRequest.of(1, 50, Sort.by(Sort.Direction.DESC, "userId")));
    }

    @Test
    void shouldGetSingleUser() {
        //given
        given(userRepository.findById(1L)).willReturn(Optional.of(u1));

        //when
        UserDto result = userService.getSingleUser(1L);

        //then
        assertUserFields(result);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldGetUserInfo() {
        //given
        given(userRepository.findById(1L)).willReturn(Optional.of(u1));

        //when
        User result = userService.getUserInfo(1L);

        //then
        assertUserFields(result);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldAddUser() {
        //given
        given(userRepository.save(u1)).willReturn(u1);

        //when
        User result = userService.addUser(u1);

        //then
        assertUserFields(result);
        verify(userRepository, times(1)).save(u1);
    }

    @Test
    void shouldEditSingleUser() {
        //given
        given(userRepository.findById(1L)).willReturn(Optional.of(u1));
        given(userRepository.save(u1)).willReturn(u1);

        //when
        UserDto result = userService.editSingleUser(u1,1L);

        //then
        assertUserFields(result);
        verify(userRepository, times(1)).save(u1);
    }

    @Test
    void shouldEditUser() {
        //given
        given(userRepository.findById(1L)).willReturn(Optional.of(u1));
        given(userRepository.save(u1)).willReturn(u1);

        //when
        UserDto result = userService.editUser(u1);

        //then
        assertUserFields(result);
        verify(userRepository, times(1)).save(u1);
    }

    @Test
    void shouldDeleteUser() {
        //given
        userRepository.deleteById(1L);

        //then
        verify(userRepository, times(1)).deleteById(1L);
    }

    private List<User> prepareUserData(){
        List<User> users = new ArrayList<>();
        users.add(u1);
        users.add(u2);
        users.add(u3);
        return users;
    }

    private void assertUserFields(UserDto user) {
        assertThat(user.getUserId()).isInstanceOf(Long.class);
        assertThat(user.getFirstName()).isEqualTo("Tomasz");
        assertThat(user.getLastName()).isEqualTo("Cichocki");
        assertThat(user.getEmail()).isEqualTo("tomasz@op.pl");
        assertThat(user.getRole()).isEqualTo(Role.user);
        assertThat(user.getDetails()).isEqualTo(null);
        assertThat(user.getFriends()).isEqualTo(null);
        assertThat(user.getPosts()).isEqualTo(null);
    }

    private void assertUserFields(User user) {
        assertThat(user.getUserId()).isInstanceOf(Long.class);
        assertThat(user.getFirstName()).isEqualTo("Tomasz");
        assertThat(user.getLastName()).isEqualTo("Cichocki");
        assertThat(user.getEmail()).isEqualTo("tomasz@op.pl");
        assertThat(user.getRole()).isEqualTo(Role.user);
        assertThat(user.getDetails()).isEqualTo(null);
        assertThat(user.getFriends()).isEqualTo(null);
        assertThat(user.getPosts()).isEqualTo(null);
    }
}