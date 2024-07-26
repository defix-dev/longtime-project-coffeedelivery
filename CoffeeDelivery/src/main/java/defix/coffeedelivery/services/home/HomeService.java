package defix.coffeedelivery.services.home;

import defix.coffeedelivery.services.account.AccountService;
import defix.coffeedelivery.services.feedback.FeedbackDTO;
import defix.coffeedelivery.services.feedback.FeedbackService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class HomeService {
    private final FeedbackService feedbackService;
    private final AccountService accountService;

    @Autowired
    public HomeService(FeedbackService feedbackService, AccountService accountService) {
        this.feedbackService = feedbackService;
        this.accountService = accountService;
    }

    public boolean isAuthenticated() {
        return accountService.isAuthenticated();
    }

    public void setupFeedbackOptions(HttpSession session) {
        session.setAttribute("feedback_last_id", feedbackService.getFirstFeedbackId());
    }

    public LinkedHashMap<String, FeedbackDTO> loadFeedbacks(HttpSession session) {
        LinkedHashMap<String, FeedbackDTO> feedbacks = feedbackService.prepareFeedbacksToDTO(3,
                (Integer) session.getAttribute("feedback_last_id"));
        Map.Entry<String, FeedbackDTO> lastFeedback = null;
        for (Map.Entry<String, FeedbackDTO> entry : feedbacks.entrySet()) {
            lastFeedback = entry;
        }
        session.setAttribute("feedback_last_id", Integer.parseInt(lastFeedback.getKey())+1);
        return feedbacks;
    }

    public void postFeedback(String feedback) {
        feedbackService.postFeedback(accountService.getCurrentAccount().getId(), feedback);
    }
}
