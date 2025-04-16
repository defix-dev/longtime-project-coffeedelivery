package defix.coffeedelivery.logout.api.controller;

import defix.coffeedelivery.common.config.URIConstant;
import defix.coffeedelivery.redirect.service.redirector.Redirect;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    @GetMapping("/success_logout")
    public String logout() {
        return Redirect.changePage(URIConstant.SUCCESS_LOGOUT);
    }

}
