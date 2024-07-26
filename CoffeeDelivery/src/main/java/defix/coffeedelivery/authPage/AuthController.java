package defix.coffeedelivery.authPage;

import defix.coffeedelivery.configurations.URLConstant;
import defix.coffeedelivery.services.account.AccountService;
import defix.coffeedelivery.services.account.RegisterAccountDTO;
import defix.coffeedelivery.services.redirectors.Redirect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/authentication")
public class AuthController {
    private final AccountService accountService;

    @Autowired
    public AuthController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public String auth() {
        return Redirect.changePage(URLConstant.AUTH);
    }

    @GetMapping("/check_account_status")
    @ResponseBody
    public ResponseEntity<Boolean> checkAccountStatus() {
        return ResponseEntity.ok().body(accountService.isAuthenticated());
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterAccountDTO accountData) {
        accountService.registerAccount(accountData);
        return ResponseEntity.ok().build();
    }
}
