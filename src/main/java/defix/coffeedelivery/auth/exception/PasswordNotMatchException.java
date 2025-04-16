package defix.coffeedelivery.auth.exception;

public class PasswordNotMatchException extends RuntimeException {
    public PasswordNotMatchException() {
        super("Password Do Not Match");
    }
}
