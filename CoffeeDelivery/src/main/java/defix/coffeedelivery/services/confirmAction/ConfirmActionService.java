package defix.coffeedelivery.services.confirmAction;

import defix.coffeedelivery.services.confirmAction.exceptions.PasswordDoNotMatchException;
import defix.coffeedelivery.services.account.AccountService;
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

    public void checkPassword(String password) throws PasswordDoNotMatchException {
        String currentPassword = accountService.getCurrentAccount().getPassword();
        if(!passwordEncoder.matches(password, currentPassword)) {
            throw new PasswordDoNotMatchException();
        }
    }
}
