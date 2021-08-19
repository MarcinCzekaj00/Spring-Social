package pl.czekaj.springsocial.exception.commentException;

public class AddCommentException extends RuntimeException{

    public AddCommentException(){
        super("Could not add comment");
    }
}
