package defix.coffeedelivery.redirect.service.redirector;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
public class SuccessRedirect implements IRedirect {
    private String targetUrl;
    private RedirectAttributes attributes;

    @Override
    public String redirect() {
        return Redirect.buildRedirectUrl(targetUrl);
    }

    @Override
    public void executeLogic() {
        attributes.addFlashAttribute("http_status", HttpStatus.OK);
    }
}
