package defix.coffeedelivery.basket.api.advice;

import defix.coffeedelivery.basket.exception.BasketNotFoundException;
import defix.coffeedelivery.basket.exception.BasketNotExistsOnAccountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class BasketControllerAdvice {
    @ExceptionHandler(BasketNotExistsOnAccountException.class)
    public ResponseEntity<String> handleBasketIsNotExistsOnAccountException(BasketNotExistsOnAccountException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(BasketNotFoundException.class)
    public ResponseEntity<String> handleBasketIsNotExistsOnAccountException(BasketNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
