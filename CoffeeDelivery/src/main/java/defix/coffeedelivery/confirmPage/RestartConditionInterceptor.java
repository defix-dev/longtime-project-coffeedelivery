package defix.coffeedelivery.confirmPage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class RestartConditionInterceptor implements HandlerInterceptor {

    private static final String CURRENT_CONFIRM_URL = "http://localhost:8888/confirm_action";

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if(request.getRequestURL().toString().equals(CURRENT_CONFIRM_URL)) {
            HttpSession session = request.getSession();
            session.removeAttribute("request_action");
        }
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
