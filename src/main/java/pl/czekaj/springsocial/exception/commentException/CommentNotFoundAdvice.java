package pl.czekaj.springsocial.exception.commentException;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.czekaj.springsocial.exception.ApiException;

import java.time.LocalDateTime;

@ControllerAdvice
public class CommentNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ApiException> commentExceptionHandler(CommentNotFoundException e){
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiException exception = new ApiException(
                e.getMessage(),
                notFound,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exception,notFound);
    }
}
