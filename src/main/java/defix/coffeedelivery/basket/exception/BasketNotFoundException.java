package defix.coffeedelivery.basket.exception;

public class BasketNotFoundException extends RuntimeException {
    public BasketNotFoundException() {
        super("Basket do not found");
    }
}
