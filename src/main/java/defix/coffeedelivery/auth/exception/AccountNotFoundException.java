package defix.coffeedelivery.auth.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException() {
        super("Account Do Not Found");
    }
}
