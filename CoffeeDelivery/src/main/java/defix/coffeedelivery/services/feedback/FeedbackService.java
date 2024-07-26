package defix.coffeedelivery.services.feedback;

import defix.coffeedelivery.services.account.AccountService;
import defix.coffeedelivery.services.feedback.exceptions.FeedbackDoNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.resps.LCSMatchResult;

import javax.validation.constraints.Null;
import java.util.*;

@Service
public class FeedbackService {
    private static final String feedbackPrefix = "FEEDBACK";

    private final RedisTemplate<String, Object> redisTemplate;
    private final AccountService accountService;

    @Autowired
    public FeedbackService(RedisTemplate<String, Object> redisTemplate, AccountService accountService) {
        this.redisTemplate = redisTemplate;
        this.accountService = accountService;
    }

    public void postFeedback(int accountId, String feedback) {
        Feedback feedbackObject = new Feedback(generateFeedbackId(), accountId, feedback);
        redisTemplate.opsForHash().put(feedbackPrefix, feedbackObject.getUniqueKey().toString(), feedbackObject);
    }

    public Map<String, Feedback> getFeedbacksWithLimit(int limit, int offset) {
        Map<String, Feedback> feedbacks = new HashMap<>();

        if(offset == getLastFeedbackId())
            return feedbacks;

        for(Integer i = offset; i < limit + offset; i++) {
            Feedback feedback = getFeedbackById(i);
            if(feedback != null) {
                feedbacks.put(i.toString(), feedback);
            }
        }

        return feedbacks;
    }

    public Map<String, Feedback> getAllFeedbacks() {
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(feedbackPrefix);
        Map<String, Feedback> feedbackMap = new HashMap<>();
        for (Map.Entry<Object, Object> entry : entries.entrySet()) {
            feedbackMap.put(entry.getKey().toString(), (Feedback) entry.getValue());
        }
        return feedbackMap;
    }

    public int getLastFeedbackId() {
        return getAllFeedbacks().values().stream()
                .map(Feedback::getId).max(Integer::compareTo).orElse(0);
    }

    public int getFirstFeedbackId() {
        return getAllFeedbacks().values().stream()
                .map(Feedback::getId).min(Integer::compareTo).orElse(0);
    }

    private int generateFeedbackId() {
        return getLastFeedbackId()+1;
    }

    private Feedback getFeedbackById(Integer id) throws FeedbackDoNotFound {
        return (Feedback)
                redisTemplate.opsForHash().values(feedbackPrefix)
                        .stream()
                        .filter(feedback -> ((Feedback)feedback).getId() == id).findAny()
                        .orElseThrow(FeedbackDoNotFound::new);
    }

    public LinkedHashMap<String, FeedbackDTO> prepareFeedbacksToDTO(int limit, int offset) {
        LinkedHashMap<String, FeedbackDTO> feedbacks = new LinkedHashMap<>();
        for (Feedback feedback : getFeedbacksWithLimit(limit, offset).values()) {
            feedbacks.put(String.valueOf(feedback.getId()),
                    new FeedbackDTO(feedback.getFeedback(),
                            accountService
                                    .findAccountById(feedback.getAccountId()).getName()));
        }
        return feedbacks;
    }
}
