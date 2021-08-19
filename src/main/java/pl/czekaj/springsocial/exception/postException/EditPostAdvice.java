package pl.czekaj.springsocial.exception.postException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.czekaj.springsocial.exception.ApiException;

import java.time.LocalDateTime;

@ControllerAdvice
public class EditPostAdvice {

    @ResponseBody
    @ExceptionHandler(AddPostException.class)
    public ResponseEntity<ApiException> editPostExceptionHandler(AddPostException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException exception = new ApiException(
                e.getMessage(),
                badRequest,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exception,badRequest);
    }
}
