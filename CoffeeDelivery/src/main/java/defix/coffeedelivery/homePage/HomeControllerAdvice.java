package defix.coffeedelivery.homePage;

import defix.coffeedelivery.services.feedback.exceptions.FeedbackDoNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HomeControllerAdvice {
    @ExceptionHandler(FeedbackDoNotFound.class)
    public ResponseEntity<String> handleFeedbackDoNotFound(FeedbackDoNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
