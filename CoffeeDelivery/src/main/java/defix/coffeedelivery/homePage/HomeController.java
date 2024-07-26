package defix.coffeedelivery.homePage;

import defix.coffeedelivery.configurations.URLConstant;
import defix.coffeedelivery.services.account.AccountService;
import defix.coffeedelivery.services.feedback.Feedback;
import defix.coffeedelivery.services.feedback.FeedbackDTO;
import defix.coffeedelivery.services.feedback.FeedbackService;
import defix.coffeedelivery.services.home.HomeService;
import defix.coffeedelivery.services.redirectors.Redirect;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {
    private final HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @ModelAttribute("is_authenticated")
    public boolean isAuthenticated() {
        return homeService.isAuthenticated();
    }

    @GetMapping({"","/"})
    public String redirectToHomePage(HttpSession session) {
        return homePage(session);
    }

    @GetMapping("/home")
    public String homePage(HttpSession session) {
        homeService.setupFeedbackOptions(session);
        return Redirect.changePage(URLConstant.HOME);
    }

    @GetMapping("/home/load_feedbacks")
    @ResponseBody
    public ResponseEntity<Map<String, FeedbackDTO>> loadFeedbacks(HttpSession session) {
        return ResponseEntity.ok(homeService.loadFeedbacks(session));
    }

    @PostMapping("/home/post_feedback")
    public ResponseEntity<Void> postFeedback(@RequestParam("feedback") String feedback) {
        homeService.postFeedback(feedback);
        return ResponseEntity.ok().build();
    }
}
