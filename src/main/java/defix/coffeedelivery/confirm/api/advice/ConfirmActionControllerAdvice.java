package defix.coffeedelivery.confirm.api.advice;

import defix.coffeedelivery.confirm.exception.PasswordNotMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ConfirmActionControllerAdvice {
    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<String> handlePasswordDoNotMatchException(PasswordNotMatchException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }
}
