package defix.coffeedelivery.logoutPage;

import defix.coffeedelivery.configurations.URLConstant;
import defix.coffeedelivery.services.redirectors.Redirect;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    @GetMapping("/success_logout")
    public String logout() {
        return Redirect.changePage(URLConstant.SUCCESS_LOGOUT);
    }

}
