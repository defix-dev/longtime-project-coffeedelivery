package defix.coffeedelivery.confirm.api.controller;

import defix.coffeedelivery.common.config.URIConstant;
import defix.coffeedelivery.confirm.service.requestAction.IRequestAction;
import defix.coffeedelivery.confirm.service.ConfirmActionService;
import defix.coffeedelivery.confirm.interceptor.IRestartCondition;
import defix.coffeedelivery.redirect.service.redirector.ErrorRedirect;
import defix.coffeedelivery.redirect.service.redirector.Redirect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("request_action")
@RequestMapping("/confirm_action")
public class ConfirmActionController implements IRestartCondition {
    private final ConfirmActionService confirmActionService;

    private IRequestAction requestAction;

    @Autowired
    public ConfirmActionController(ConfirmActionService confirmActionService) {
        this.confirmActionService = confirmActionService;
    }

    @GetMapping
    public String confirmAction(Model model) {
        if (!model.containsAttribute("request_action")) {
            return Redirect.redirect(new ErrorRedirect(HttpStatus.BAD_REQUEST));
        } else {
            requestAction = (IRequestAction) model.getAttribute("request_action");
        }

        return Redirect.changePage(URIConstant.CONFIRM_ACTION);
    }

    @PostMapping("/check_password")
    public ResponseEntity<Void> executeAction(@RequestParam("confirm-password") String password) {
        confirmActionService.checkPassword(password);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(requestAction.getLocation());
        requestAction.executeRequest();
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @Override
    public String[] getAttributes() {
        return new String[]{
                "request_action"
        };
    }

    @Override
    public String getUrl() {
        return "/confirm_action";
    }
}
