package defix.coffeedelivery.services.product.exceptions;

public class ProductDoNotFoundException extends RuntimeException {
    public ProductDoNotFoundException() {
        super("Product Do Not Found");
    }
}
