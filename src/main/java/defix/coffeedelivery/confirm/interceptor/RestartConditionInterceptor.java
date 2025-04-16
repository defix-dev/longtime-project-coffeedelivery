package defix.coffeedelivery.confirm.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class RestartConditionInterceptor implements HandlerInterceptor {
    private final IRestartCondition[] restartConditions;

    public RestartConditionInterceptor(IRestartCondition... restartConditions) {
        this.restartConditions = restartConditions;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        for (IRestartCondition restartCondition : restartConditions) {
            if(request.getRequestURL().toString().equals(buildFullUrl(restartCondition.getUrl()))) {
                HttpSession session = request.getSession();
                for(String attribute : restartCondition.getAttributes())
                    session.removeAttribute(attribute);
            }
        }
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    private static String buildFullUrl(String url) {
        return "http://localhost:8888" + url;
    }
}
