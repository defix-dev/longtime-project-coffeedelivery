package defix.coffeedelivery.services.basket.exceptions;

public class BasketIsNotExistsOnAccountException extends RuntimeException {
    public BasketIsNotExistsOnAccountException() {
        super("Basket in not exists on account");
    }
}
