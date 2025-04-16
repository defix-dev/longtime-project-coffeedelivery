package defix.coffeedelivery.redirect.service.redirector;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class ErrorRedirect implements IRedirect {
    private final HttpStatus status;
    private String finalUrl;

    @Override
    public String redirect() {
        return finalUrl;
    }

    @Override
    public void executeLogic() {
        finalUrl = Redirect.buildRedirectUrl("error?status=" + status.value() + " " + status.getReasonPhrase());
    }
}
