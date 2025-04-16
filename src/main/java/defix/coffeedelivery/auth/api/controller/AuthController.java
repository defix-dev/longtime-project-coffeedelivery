package defix.coffeedelivery.auth.api.controller;

import defix.coffeedelivery.common.config.URIConstant;
import defix.coffeedelivery.auth.service.AccountService;
import defix.coffeedelivery.auth.api.dto.request.RegisterAccountDTO;
import defix.coffeedelivery.redirect.service.redirector.Redirect;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/authentication")
@Validated
public class AuthController {
    private final AccountService accountService;

    @Autowired
    public AuthController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public String auth() {
        return Redirect.changePage(URIConstant.AUTH);
    }

    @GetMapping("/check_account_status")
    @ResponseBody
    public ResponseEntity<Boolean> checkAccountStatus() {
        return ResponseEntity.ok().body(accountService.isAuthenticated());
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterAccountDTO accountData) {
        accountService.registerAccount(accountData);
        return ResponseEntity.ok().build();
    }
}
