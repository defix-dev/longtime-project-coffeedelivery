package defix.coffeedelivery.catalogPage;

import defix.coffeedelivery.services.filter.exceptions.ProductsDoNotFoundException;
import defix.coffeedelivery.services.product.exceptions.ProductDoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class CatalogAdviceController {
    @ExceptionHandler(ProductDoNotFoundException.class)
    public ResponseEntity<String> handleProductDoNotFound(ProductDoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ProductsDoNotFoundException.class)
    public ResponseEntity<String> handleProductDoNotFound(ProductsDoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
