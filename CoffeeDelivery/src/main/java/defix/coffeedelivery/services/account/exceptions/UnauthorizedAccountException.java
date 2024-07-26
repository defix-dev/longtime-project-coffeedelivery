package defix.coffeedelivery.services.account.exceptions;

public class UnauthorizedAccountException extends RuntimeException {
    public UnauthorizedAccountException() {
        super("Unauthorized account");
    }
}
