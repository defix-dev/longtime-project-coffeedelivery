package defix.coffeedelivery.services.basket.exceptions;

public class BasketDoNotFound extends RuntimeException {
    public BasketDoNotFound() {
        super("Basket do not found");
    }
}
