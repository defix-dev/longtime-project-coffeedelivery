package defix.coffeedelivery.home.exception;

public class FeedbackNotFoundException extends RuntimeException {
    public FeedbackNotFoundException() {
        super("Feedback Do Not Found");
    }
}
