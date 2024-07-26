package defix.coffeedelivery.services.account.exceptions;

import defix.coffeedelivery.services.account.RegisterAccountDTO;
import lombok.Getter;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Getter
public class AccountConstraintException extends RuntimeException {
    private final Set<ConstraintViolation<RegisterAccountDTO>> violations;

    public AccountConstraintException(Set<ConstraintViolation<RegisterAccountDTO>> violations) {
        super("Account constraint exception (Has more errors)");
        this.violations = violations;
    }
}
