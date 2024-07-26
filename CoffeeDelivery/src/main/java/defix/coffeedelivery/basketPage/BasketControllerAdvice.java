package defix.coffeedelivery.basketPage;

import defix.coffeedelivery.services.basket.exceptions.BasketDoNotFound;
import defix.coffeedelivery.services.basket.exceptions.BasketIsNotExistsOnAccountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class BasketControllerAdvice {
    @ExceptionHandler(BasketIsNotExistsOnAccountException.class)
    public ResponseEntity<String> handleBasketIsNotExistsOnAccountException(BasketIsNotExistsOnAccountException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(BasketDoNotFound.class)
    public ResponseEntity<String> handleBasketIsNotExistsOnAccountException(BasketDoNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
