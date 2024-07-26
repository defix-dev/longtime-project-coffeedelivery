package defix.coffeedelivery.services.feedback.exceptions;

public class FeedbackDoNotFound extends RuntimeException {
    public FeedbackDoNotFound() {
        super("Feedback Do Not Found");
    }
}
