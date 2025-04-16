package defix.coffeedelivery.auth.exception;

import defix.coffeedelivery.auth.api.dto.request.RegisterAccountDTO;
import lombok.Getter;

import jakarta.validation.ConstraintViolation;
import java.util.Set;

@Getter
public class AccountConstraintException extends RuntimeException {
    private final Set<ConstraintViolation<RegisterAccountDTO>> violations;

    public AccountConstraintException(Set<ConstraintViolation<RegisterAccountDTO>> violations) {
        super("Account constraint exception (Has more errors)");
        this.violations = violations;
    }
}
