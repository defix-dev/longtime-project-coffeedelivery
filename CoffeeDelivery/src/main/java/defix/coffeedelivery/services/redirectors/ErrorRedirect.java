package defix.coffeedelivery.services.redirectors;

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
        finalUrl = Redirect.buildRedirectUrl("site_error?status=" + status.value() + " " + status.getReasonPhrase());
    }
}
