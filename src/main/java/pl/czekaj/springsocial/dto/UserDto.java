package pl.czekaj.springsocial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.czekaj.springsocial.enums.Role;
import pl.czekaj.springsocial.model.Relationship;
import pl.czekaj.springsocial.model.UserDetails;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
public class UserDto {

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private UserDetails details;
    private Set<Relationship> friends;

}
