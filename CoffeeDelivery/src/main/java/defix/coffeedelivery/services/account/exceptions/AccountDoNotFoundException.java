package defix.coffeedelivery.services.account.exceptions;

public class AccountDoNotFoundException extends RuntimeException {
    public AccountDoNotFoundException() {
        super("Account Do Not Found");
    }
}
