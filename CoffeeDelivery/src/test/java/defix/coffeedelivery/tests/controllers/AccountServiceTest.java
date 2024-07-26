package defix.coffeedelivery.tests.controllers;

import defix.coffeedelivery.db.entity.Account;
import defix.coffeedelivery.db.repositories.AccountRepository;
import defix.coffeedelivery.services.account.AccountService;
import defix.coffeedelivery.services.account.RegisterAccountDTO;
import defix.coffeedelivery.services.account.exceptions.AccountAlreadyExistsException;
import defix.coffeedelivery.services.account.exceptions.AccountDoNotFoundException;
import defix.coffeedelivery.services.account.exceptions.PasswordDoNotMatchException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AccountService accountService;

    @Mock
    private BindingResult bindingResult;

    public AccountServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterAccountSuccess() throws Exception {
        RegisterAccountDTO dto = new RegisterAccountDTO("12345", "John Doe", "password", "password");
        when(accountRepository.getAccountByPhoneNumber(dto.getPhoneNumber())).thenReturn(null);

        accountService.registerAccount(dto);

        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    public void testRegisterAccountFailureDueToExistingAccount() throws Exception {
        RegisterAccountDTO dto = new RegisterAccountDTO("1234", "John Doe", "password", "password");
        when(accountRepository.getAccountByPhoneNumber(dto.getPhoneNumber())).thenReturn(new Account());

        assertThrows(AccountAlreadyExistsException.class, () -> {
            accountService.registerAccount(dto);
        });
    }

    @Test
    public void testRegisterAccountFailureDueToValidationErrors() throws Exception {
        RegisterAccountDTO dto = new RegisterAccountDTO("1234567890", "password", "mismatch", "John Doe");
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        assertThrows(PasswordDoNotMatchException.class, () -> {
            accountService.registerAccount(dto);
        });
    }

    @Test
    public void testFindAccountByPhoneNumberSuccess() throws Exception {
        String phoneNumber = "1234567890";
        Account account = new Account();
        when(accountRepository.getAccountByPhoneNumber(phoneNumber)).thenReturn(account);

        Account result = accountService.findAccountByPhoneNumber(phoneNumber);

        assertNotNull(result);
    }

    @Test
    public void testFindAccountByPhoneNumberFailure() throws Exception {
        String phoneNumber = "1234567890";
        when(accountRepository.getAccountByPhoneNumber(phoneNumber)).thenReturn(null);

        assertThrows(AccountDoNotFoundException.class, () -> {
            accountService.findAccountByPhoneNumber(phoneNumber);
        });
    }

    // Добавьте больше тестов для других методов сервиса по аналогии
}