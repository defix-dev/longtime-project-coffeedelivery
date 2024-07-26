package defix.coffeedelivery.services.account.exceptions;

public class PasswordDoNotMatchException extends RuntimeException {
    public PasswordDoNotMatchException() {
        super("Password Do Not Match");
    }
}
