package defix.coffeedelivery.auth.api.controller;

import defix.coffeedelivery.auth.api.dto.request.RegisterAccountDTO;
import defix.coffeedelivery.auth.service.AccountService;
import defix.coffeedelivery.common.config.URIConstant;
import defix.coffeedelivery.redirect.service.redirector.Redirect;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller responsible for authentication-related endpoints such as registration,
 * authentication status check, and returning the authentication page.
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AccountService accountService;

    /**
     * Constructor for injecting the {@link AccountService}.
     *
     * @param accountService the account service to be injected
     */
    @Autowired
    public AuthController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Returns the authentication page as an HTML response.
     *
     * @return HTML content of the authentication page
     */
    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String auth() {
        return Redirect.changePage(URIConstant.AUTH);
    }

    /**
     * Checks if the current user is authenticated.
     *
     * @return {@link ResponseEntity} with a boolean value:
     *         {@code true} if the user is authenticated,
     *         {@code false} otherwise
     */
    @GetMapping("/authorized")
    @ResponseBody
    public ResponseEntity<Boolean> checkAccountStatus() {
        return ResponseEntity.ok().body(accountService.isAuthenticated());
    }

    /**
     * Registers a new account based on the provided user data.
     *
     * @param accountData the registration data from the request body
     * @return empty {@link ResponseEntity} with status 200 OK if registration succeeds
     */
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterAccountDTO accountData) {
        accountService.registerAccount(accountData);
        return ResponseEntity.ok().build();
    }
}

