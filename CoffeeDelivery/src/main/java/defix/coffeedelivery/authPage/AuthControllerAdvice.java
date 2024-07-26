package defix.coffeedelivery.authPage;

import defix.coffeedelivery.services.account.RegisterAccountDTO;
import defix.coffeedelivery.services.account.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class AuthControllerAdvice {
    @ExceptionHandler(PasswordDoNotMatchException.class)
    public ResponseEntity<String> handleBadAccountDataFormat(PasswordDoNotMatchException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFound(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(AccountDoNotFoundException.class)
    public ResponseEntity<String> handleAccountDoNotFound(AccountDoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<String> handleAccountAlreadyExists(AccountAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedAccountException.class)
    public ResponseEntity<String> handleUnauthorizedAccount(UnauthorizedAccountException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(AccountConstraintException.class)
    public ResponseEntity<List<Map.Entry<String, String>>> handleAccountConstraint(AccountConstraintException ex) {
        List<Map.Entry<String, String>> violations = new ArrayList<>();
        for(ConstraintViolation<RegisterAccountDTO> violation : ex.getViolations()) {
            violations.add(Map.entry(violation.getPropertyPath().toString(),
                    violation.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(violations);
    }
}
