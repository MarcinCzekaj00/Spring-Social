package pl.czekaj.springsocial.exception.postException;

public class PostNotFoundException extends RuntimeException{

    public PostNotFoundException(Long id){
        super("Could not find post: "+ id);
    }

    public PostNotFoundException(){
        super("Could not find posts");
    }

}
