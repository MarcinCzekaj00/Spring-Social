package pl.czekaj.springsocial.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.czekaj.springsocial.dto.UserDto;
import pl.czekaj.springsocial.enums.Role;
import pl.czekaj.springsocial.model.Post;
import pl.czekaj.springsocial.model.Relationship;
import pl.czekaj.springsocial.model.User;
import pl.czekaj.springsocial.model.UserDetails;
import pl.czekaj.springsocial.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "password", roles = "ADMIN")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void shouldGetUsers() throws Exception{

        //when
        MvcResult result = mockMvc.perform(get("/users").content(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        //then
        UserDto user = objectMapper.readValue(result.getResponse().getContentAsString(), UserDto.class);
        assertThat(user).isNotNull();
    }

    @Test
    @Transactional
    void shouldGetUser() throws Exception {

        //given
        User u2 = new User();
        u2.setFirstName("Tomasz");
        u2.setLastName("Cichocki");
        u2.setEmail("testing@op.pl");
        u2.setPassword("password");
        u2.setRole(Role.user);
        u2.setDetails(new UserDetails());
        u2.setFriends(Set.of(new Relationship(1L,2L,1L)));
        u2.setPosts(Set.of(new Post(1L,"content", LocalDateTime.now(),null)));
        userRepository.save(u2);

        //when
        MvcResult result = mockMvc.perform(get("/users/" + u2.getUserId()).content(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        //then
        User user = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
        assertThat(user).isNotNull();
        assertThat(user.getUserId()).isEqualTo(u2.getUserId());
        assertThat(user.getEmail()).isEqualTo("testing@op.pl");
        assertThat(user.getFirstName()).isEqualTo("Tomasz");
    }

    @Test
    @Transactional
    void shouldGetUserInfo() throws Exception {

        //given
        User u2 = new User();
        u2.setFirstName("Tomasz");
        u2.setLastName("Cichocki");
        u2.setEmail("testing@op.pl");
        u2.setPassword("password");
        u2.setRole(Role.user);
        u2.setDetails(new UserDetails());
        u2.setFriends(Set.of(new Relationship(1L,2L,1L)));
        u2.setPosts(Set.of(new Post(1L,"content", LocalDateTime.now(),null)));
        userRepository.save(u2);

        //when
        MvcResult result = mockMvc.perform(get("/users/" + u2.getUserId() + "/info").content(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        //then
        User user = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
        assertThat(user).isNotNull();
        assertThat(user.getUserId()).isEqualTo(u2.getUserId());
        assertThat(user.getEmail()).isEqualTo("testing@op.pl");
        assertThat(user.getFirstName()).isEqualTo("Tomasz");
    }

    @Test
    @Transactional
    void shouldAddUser() throws Exception {
        //given
        User u2 = new User();
        u2.setFirstName("Tomasz");
        u2.setLastName("Cichocki");
        u2.setEmail("testing@op.pl");
        u2.setPassword("password");
        u2.setRole(Role.user);
        u2.setFriends(Collections.<Relationship>emptySet());
        u2.setPosts(Collections.<Post>emptySet());

        String jsonRequest = objectMapper.writeValueAsString(u2);

        //when
        MvcResult result = mockMvc.perform(post("/users").content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        //then
        User user = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("testing@op.pl");
        assertThat(user.getFirstName()).isEqualTo("Tomasz");
        assertThat(user.getLastName()).isEqualTo("Cichocki");
    }

    @Test
    @Transactional
    void shouldEditUser() throws Exception {
        //given
        User u2 = new User();
        u2.setFirstName("Tomasz");
        u2.setLastName("Cichocki");
        u2.setEmail("testing@op.pl");
        u2.setPassword("password");
        u2.setRole(Role.user);
        u2.setFriends(Collections.<Relationship>emptySet());
        u2.setPosts(Collections.<Post>emptySet());
        userRepository.save(u2);

        String jsonRequest = objectMapper.writeValueAsString(u2);

        //when
        MvcResult result = mockMvc.perform(put("/users").content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        //then
        User user = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("testing@op.pl");
        assertThat(user.getFirstName()).isEqualTo("Tomasz");
        assertThat(user.getLastName()).isEqualTo("Cichocki");

    }

    @Test
    @Transactional
    void shouldEditSingleUser() throws Exception {

        //given
        User u2 = new User();
        u2.setFirstName("Tomasz");
        u2.setLastName("Cichocki");
        u2.setEmail("testing@op.pl");
        u2.setPassword("password");
        u2.setRole(Role.user);
        u2.setFriends(Collections.<Relationship>emptySet());
        u2.setPosts(Collections.<Post>emptySet());
        userRepository.save(u2);

        String jsonRequest = objectMapper.writeValueAsString(u2);

        //when
        MvcResult result = mockMvc.perform(put("/users/" + u2.getUserId()).content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        //then
        User user = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("testing@op.pl");
        assertThat(user.getFirstName()).isEqualTo("Tomasz");
        assertThat(user.getLastName()).isEqualTo("Cichocki");

    }

    @Test
    @Transactional
    void deleteUser() throws Exception {

        //given
        User u2 = new User();
        u2.setFirstName("Tomasz");
        u2.setLastName("Cichocki");
        u2.setEmail("testing@op.pl");
        u2.setPassword("password");
        u2.setRole(Role.user);
        u2.setFriends(Collections.<Relationship>emptySet());
        u2.setPosts(Collections.<Post>emptySet());
        userRepository.save(u2);

        String jsonRequest = objectMapper.writeValueAsString(u2.getUserId());

        //when
        mockMvc.perform(delete("/users/" + u2.getUserId()).content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());

        //then
    }

}