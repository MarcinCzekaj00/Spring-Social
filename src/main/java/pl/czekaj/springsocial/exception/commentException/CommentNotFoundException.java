package pl.czekaj.springsocial.exception.commentException;

public class CommentNotFoundException extends RuntimeException{

    public CommentNotFoundException(Long id){
        super("Could not find comment: "+ id);
    }

    public CommentNotFoundException(){
        super("Could not find comments");
    }
}
