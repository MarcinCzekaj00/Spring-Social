package pl.czekaj.springsocial.exception.commentException;

public class EditCommentException extends RuntimeException{

    public EditCommentException(Long id){
        super("Could not edit comment: "+ id);
    }

}
