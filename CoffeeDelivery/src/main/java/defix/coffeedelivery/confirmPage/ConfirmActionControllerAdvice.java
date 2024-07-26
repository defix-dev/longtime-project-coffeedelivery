package defix.coffeedelivery.confirmPage;

import defix.coffeedelivery.services.confirmAction.exceptions.PasswordDoNotMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ConfirmActionControllerAdvice {
    @ExceptionHandler(PasswordDoNotMatchException.class)
    public ResponseEntity<String> handlePasswordDoNotMatchException(PasswordDoNotMatchException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }
}
