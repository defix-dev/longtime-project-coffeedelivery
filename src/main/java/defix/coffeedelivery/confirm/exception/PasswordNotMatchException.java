package defix.coffeedelivery.confirm.exception;

public class PasswordNotMatchException extends RuntimeException {
    public PasswordNotMatchException() {
        super("Password does not match");
    }
}
