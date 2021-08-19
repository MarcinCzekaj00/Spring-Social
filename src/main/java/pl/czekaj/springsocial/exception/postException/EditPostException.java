package pl.czekaj.springsocial.exception.postException;

public class EditPostException extends RuntimeException{

    public EditPostException(Long id){
        super("Could not edit post: "+ id);
    }
}
