package defix.coffeedelivery.contactDataPage;

import defix.coffeedelivery.configurations.URLConstant;
import defix.coffeedelivery.confirmPage.RequestActions.EditContactDataRequestAction;
import defix.coffeedelivery.db.entity.Account;
import defix.coffeedelivery.services.account.AccountDTO;
import defix.coffeedelivery.services.account.AccountService;
import defix.coffeedelivery.services.account.ContactAccountDTO;
import defix.coffeedelivery.services.basket.BasketService;
import defix.coffeedelivery.services.redirectors.Redirect;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;

@Controller
@SessionAttributes({"account", "request_action"})
@RequestMapping("/edit_contact_data")
public class EditContactDataController {
    private final AccountService accountService;

    @Autowired
    public EditContactDataController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ModelAttribute("account")
    public AccountDTO account() {
        return accountService.getCurrentAccountDTO();
    }

    @GetMapping
    public String editControllerData() {
        return Redirect.changePage(URLConstant.EDIT_CONTACT_DATA);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveChanges(@RequestBody ContactAccountDTO accountDTO,
                                      Model model) {
        model
                .addAttribute("request_action",
                        new EditContactDataRequestAction(accountService, accountDTO));

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(Redirect.getLocation(URLConstant.CONFIRM_ACTION));
        return ResponseEntity.ok().headers(headers).build();
    }
}
