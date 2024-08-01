package defix.coffeedelivery.confirmPage;

import defix.coffeedelivery.configurations.URLConstant;
import defix.coffeedelivery.confirmPage.RequestActions.IRequestAction;
import defix.coffeedelivery.services.confirmAction.ConfirmActionService;
import defix.coffeedelivery.services.interceptors.restartCondition.IRestartCondition;
import defix.coffeedelivery.services.redirectors.ErrorRedirect;
import defix.coffeedelivery.services.redirectors.Redirect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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

        return Redirect.changePage(URLConstant.CONFIRM_ACTION);
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
