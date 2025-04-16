package defix.coffeedelivery.auth.api.advice;

import defix.coffeedelivery.auth.exception.*;
import defix.coffeedelivery.auth.api.dto.request.RegisterAccountDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class AuthControllerAdvice {
    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<String> handleBadAccountDataFormat(PasswordNotMatchException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFound(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountDoNotFound(AccountNotFoundException ex) {
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
