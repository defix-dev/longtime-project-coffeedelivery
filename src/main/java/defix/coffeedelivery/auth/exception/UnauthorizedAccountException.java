package defix.coffeedelivery.auth.exception;

public class UnauthorizedAccountException extends RuntimeException {
    public UnauthorizedAccountException() {
        super("Unauthorized account");
    }
}
