package defix.coffeedelivery.contactData.api.controller;

import defix.coffeedelivery.common.config.URIConstant;
import defix.coffeedelivery.confirm.service.requestAction.EditContactDataRequestAction;
import defix.coffeedelivery.contactData.api.dto.response.AccountDTO;
import defix.coffeedelivery.auth.service.AccountService;
import defix.coffeedelivery.contactData.api.dto.request.ContactAccountDTO;
import defix.coffeedelivery.redirect.service.redirector.Redirect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller responsible for handling operations related to editing user contact data.
 * It includes displaying the edit page, saving changes to the contact data, and redirecting
 * to the confirmation page.
 */
@RestController
@SessionAttributes({"account", "request_action"})
public class EditContactDataController {

    private final AccountService accountService;

    /**
     * Constructor for injecting the {@link AccountService}.
     *
     * @param accountService the account service to be injected
     */
    @Autowired
    public EditContactDataController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Provides the current authenticated user's account data.
     *
     * @return the current user's {@link AccountDTO} object
     */
    @ModelAttribute("account")
    public AccountDTO account() {
        return accountService.getCurrentAccountDTO();
    }

    /**
     * Returns the edit contact data page as an HTML response.
     *
     * @return the HTML content of the edit contact data page
     */
    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String editControllerData() {
        return Redirect.changePage(URIConstant.EDIT_CONTACT_DATA);
    }

    /**
     * Saves the changes to the user's contact data and redirects to the confirmation page.
     *
     * @param accountDTO the new contact data to be saved
     * @param model      the Spring MVC model used to store session attributes
     * @return a {@link ResponseEntity} with an HTTP 200 OK status and a location header pointing
     *         to the confirmation page
     */
    @PutMapping("/api/v1/users/me/contact_data")
    public ResponseEntity<Void> saveChanges(@RequestBody ContactAccountDTO accountDTO,
                                            Model model) {
        model.addAttribute("request_action", new EditContactDataRequestAction(accountService, accountDTO));

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(Redirect.getLocation(URIConstant.CONFIRM_ACTION));
        return ResponseEntity.ok().headers(headers).build();
    }
}
