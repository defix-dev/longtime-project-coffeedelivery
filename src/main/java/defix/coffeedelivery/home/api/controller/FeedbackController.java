package defix.coffeedelivery.home.api.controller;

import defix.coffeedelivery.home.api.dto.response.FeedbackDTO;
import defix.coffeedelivery.home.service.HomeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST controller responsible for managing feedback-related operations.
 * It allows for loading feedbacks and posting new feedback from users.
 */
@RestController
@RequestMapping("/api/v1/feedbacks")
public class FeedbackController {

    private final HomeService homeService;

    /**
     * Constructor for injecting the {@link HomeService}.
     *
     * @param homeService the home service to be injected
     */
    @Autowired
    public FeedbackController(HomeService homeService) {
        this.homeService = homeService;
    }

    /**
     * Loads the list of feedbacks from the session and returns them in a response.
     *
     * @param session the HTTP session containing user-specific data
     * @return {@link ResponseEntity} containing a map of feedbacks, where the key is a
     *         String (feedback ID) and the value is the {@link FeedbackDTO}
     */
    @GetMapping
    public ResponseEntity<Map<String, FeedbackDTO>> loadFeedbacks(HttpSession session) {
        return ResponseEntity.ok(homeService.loadFeedbacks(session));
    }

    /**
     * Posts a new feedback provided by the user.
     *
     * @param feedback the feedback text to be posted
     * @return an empty {@link ResponseEntity} with status 200 OK if the feedback is successfully posted
     */
    @PostMapping
    public ResponseEntity<Void> postFeedback(@RequestParam("feedback") String feedback) {
        homeService.postFeedback(feedback);
        return ResponseEntity.ok().build();
    }
}
