package product;

public class ProductNotFoundException extends RuntimeException {
    ProductNotFoundException(Long id) {
        super("Could not find product " + id);
    }
}
