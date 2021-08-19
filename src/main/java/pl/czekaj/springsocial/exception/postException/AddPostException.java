package pl.czekaj.springsocial.exception.postException;

public class AddPostException extends RuntimeException{

    public AddPostException(){
        super("Could not add post");
    }
}
