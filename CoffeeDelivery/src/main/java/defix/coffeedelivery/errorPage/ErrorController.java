package defix.coffeedelivery.errorPage;

import defix.coffeedelivery.configurations.URLConstant;
import defix.coffeedelivery.services.redirectors.Redirect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/site_error")
public class ErrorController {
    @GetMapping
    public String errorPage(@RequestParam String status, ModelMap model) {
        model.addAttribute("status",
                status.isEmpty() ? HttpStatus.OK.toString() : status);
        return Redirect.changePage(URLConstant.ERROR_PAGE);
    }
}
