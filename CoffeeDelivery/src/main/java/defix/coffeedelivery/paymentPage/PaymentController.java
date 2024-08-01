package defix.coffeedelivery.paymentPage;

import defix.coffeedelivery.services.interceptors.restartCondition.IRestartCondition;
import defix.coffeedelivery.services.payment.PaymentDTO;
import defix.coffeedelivery.services.redirectors.ErrorRedirect;
import defix.coffeedelivery.services.redirectors.Redirect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("payment_dto")
@RequestMapping("/payment")
public class PaymentController implements IRestartCondition {
    private PaymentDTO paymentDTO;

    @GetMapping
    private String paymentPage(Model model) {
        if(model.containsAttribute("payment_dto")) {
            paymentDTO = (PaymentDTO) model.getAttribute("payment_dto");
            return "payment_page";
        }
        else {
            return Redirect.redirect(new ErrorRedirect(HttpStatus.BAD_REQUEST));
        }
    }

    @Override
    public String[] getAttributes() {
        return new String[] {
                "payment_dto"
        };
    }

    @Override
    public String getUrl() {
        return "/payment";
    }
}
