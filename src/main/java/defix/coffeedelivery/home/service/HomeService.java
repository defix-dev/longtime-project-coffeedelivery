package defix.coffeedelivery.home.service;

import defix.coffeedelivery.auth.service.AccountService;
import defix.coffeedelivery.home.api.dto.response.FeedbackDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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

    public boolean isAuthenticated(HttpSession session) throws IOException, InterruptedException {
        CookieHandler manager = new CookieManager();
        CookieHandler.setDefault(manager);
        HttpClient client = HttpClient.newBuilder().cookieHandler(CookieHandler.getDefault()).build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8888/authentication/check_account_status"))
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + session.getId())
                .GET().build();
        String response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        return Boolean.valueOf(response);
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
