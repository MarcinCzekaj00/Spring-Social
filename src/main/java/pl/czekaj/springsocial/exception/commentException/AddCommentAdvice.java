package pl.czekaj.springsocial.exception.commentException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.czekaj.springsocial.exception.ApiException;

import java.time.LocalDateTime;

@ControllerAdvice
public class AddCommentAdvice {

    @ResponseBody
    @ExceptionHandler(AddCommentException.class)
    public ResponseEntity<ApiException> addCommentExceptionHandler(AddCommentException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException exception = new ApiException(
                e.getMessage(),
                badRequest,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exception,badRequest);
    }
}
