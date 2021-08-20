package pl.czekaj.springsocial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDetailsDto extends RepresentationModel<UserDetailsDto> {
    private Long userDetailsId;
    private LocalDateTime dateOfBirth;
    private int phoneNumber;

}

