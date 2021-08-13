package pl.czekaj.springsocial.validator;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import pl.czekaj.springsocial.repository.UserRepository;
import pl.czekaj.springsocial.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@NoArgsConstructor
@AllArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail,String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UniqueEmail constraintAnnotation){
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext cxt){
        if(userRepository == null){
            return true;
        }
        return email != null && userRepository.findByEmail(email) == null;
    }
}
