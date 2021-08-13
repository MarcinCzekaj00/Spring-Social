package pl.czekaj.springsocial.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.czekaj.springsocial.enums.Role;
import pl.czekaj.springsocial.validator.UniqueEmail;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@Entity
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Size(min = 3, message = "{pl.czekaj.model.User.firstName.GreaterThan3}")
    private String firstName;

    @Size(min = 3, message = "{pl.czekaj.model.User.lastName.GreaterThan3}")
    private String lastName;

    @NotEmpty(message = "{pl.czekaj.model.User.field.NotEmpty}")
    @Column(unique = true)
    @Email
    @UniqueEmail
    private String email;

    @Size(min = 6, message = "{pl.czekaj.model.User.password.GreaterThan6}")
    private String password;

    @Enumerated
    private Role role;

    @OneToOne(cascade = CascadeType.REMOVE,orphanRemoval = true)
    @JoinColumn(name = "user_details_id")
    private UserDetails details;

    @OneToMany(cascade = CascadeType.REMOVE,orphanRemoval = true)
    @JoinColumn(name = "friends", referencedColumnName = "userId" )
    private Set<Relationship> friends;

    @OneToMany(cascade = CascadeType.REMOVE,orphanRemoval = true)
    @JoinColumn(name = "postUserId", referencedColumnName = "userId" )
    private Set<Post> postUserId;

}
