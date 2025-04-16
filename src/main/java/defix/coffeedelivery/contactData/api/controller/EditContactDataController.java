package defix.coffeedelivery.contactData.api.controller;

import defix.coffeedelivery.common.config.URIConstant;
import defix.coffeedelivery.confirm.service.requestAction.EditContactDataRequestAction;
import defix.coffeedelivery.contactData.api.dto.response.AccountDTO;
import defix.coffeedelivery.auth.service.AccountService;
import defix.coffeedelivery.contactData.api.dto.request.ContactAccountDTO;
import defix.coffeedelivery.redirect.service.redirector.Redirect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return Redirect.changePage(URIConstant.EDIT_CONTACT_DATA);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveChanges(@RequestBody ContactAccountDTO accountDTO,
                                      Model model) {
        model
                .addAttribute("request_action",
                        new EditContactDataRequestAction(accountService, accountDTO));

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(Redirect.getLocation(URIConstant.CONFIRM_ACTION));
        return ResponseEntity.ok().headers(headers).build();
    }
}
