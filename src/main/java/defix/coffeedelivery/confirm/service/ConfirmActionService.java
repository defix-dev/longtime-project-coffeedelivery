package defix.coffeedelivery.confirm.service;

import defix.coffeedelivery.confirm.exception.PasswordNotMatchException;
import defix.coffeedelivery.auth.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ConfirmActionService {
    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;

    @Autowired
    public ConfirmActionService(PasswordEncoder passwordEncoder, AccountService accountService) {
        this.passwordEncoder = passwordEncoder;
        this.accountService = accountService;
    }

    public void checkPassword(String password) throws PasswordNotMatchException {
        String currentPassword = accountService.getCurrentAccount().getPassword();
        if(!passwordEncoder.matches(password, currentPassword)) {
            throw new PasswordNotMatchException();
        }
    }
}
