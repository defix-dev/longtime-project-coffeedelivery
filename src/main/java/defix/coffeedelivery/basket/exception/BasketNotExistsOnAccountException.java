package defix.coffeedelivery.basket.exception;

public class BasketNotExistsOnAccountException extends RuntimeException {
    public BasketNotExistsOnAccountException() {
        super("Basket in not exists on account");
    }
}
