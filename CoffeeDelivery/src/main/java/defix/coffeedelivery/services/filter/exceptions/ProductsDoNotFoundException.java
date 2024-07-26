package defix.coffeedelivery.services.filter.exceptions;

public class ProductsDoNotFoundException extends RuntimeException {
    public ProductsDoNotFoundException() {
        super("Product not found");
    }
}
