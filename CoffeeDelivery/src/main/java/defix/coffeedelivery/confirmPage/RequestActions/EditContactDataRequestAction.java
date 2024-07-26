package defix.coffeedelivery.confirmPage.RequestActions;

import defix.coffeedelivery.configurations.URLConstant;
import defix.coffeedelivery.services.account.AccountService;
import defix.coffeedelivery.services.account.ContactAccountDTO;
import defix.coffeedelivery.services.redirectors.Redirect;

import java.net.URI;

public class EditContactDataRequestAction implements IRequestAction {
    private final AccountService accountService;
    private final ContactAccountDTO account;

    public EditContactDataRequestAction(AccountService accountService, ContactAccountDTO account) {
        this.accountService = accountService;
        this.account = account;
    }

    @Override
    public URI getLocation() {
        return Redirect.getLocation(URLConstant.HOME);
    }

    @Override
    public void executeRequest() {
        accountService.updateAccountData(account, AccountService.UpdateType.ALL);
    }
}
