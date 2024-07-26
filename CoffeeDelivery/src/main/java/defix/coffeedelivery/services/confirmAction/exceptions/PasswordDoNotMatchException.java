package defix.coffeedelivery.services.confirmAction.exceptions;

public class PasswordDoNotMatchException extends RuntimeException {
    public PasswordDoNotMatchException() {
        super("Password does not match");
    }
}
