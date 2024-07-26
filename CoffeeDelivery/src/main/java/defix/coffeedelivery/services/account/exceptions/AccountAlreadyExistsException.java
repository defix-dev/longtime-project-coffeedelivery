package defix.coffeedelivery.services.account.exceptions;

public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException() {
        super("Account already exists");
    }
}
