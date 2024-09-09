package post;

public class PostNotFoundException extends RuntimeException {
    PostNotFoundException(Long id) {
        super("Could not find product " + id);
    }
}
