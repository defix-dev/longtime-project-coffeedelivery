package defix.coffeedelivery.confirm.service.requestAction;

import defix.coffeedelivery.common.config.URIConstant;
import defix.coffeedelivery.auth.service.AccountService;
import defix.coffeedelivery.contactData.api.dto.request.ContactAccountDTO;
import defix.coffeedelivery.redirect.service.redirector.Redirect;

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
        return Redirect.getLocation(URIConstant.HOME);
    }

    @Override
    public void executeRequest() {
        accountService.updateAccountData(account, AccountService.UpdateType.ALL);
    }
}
