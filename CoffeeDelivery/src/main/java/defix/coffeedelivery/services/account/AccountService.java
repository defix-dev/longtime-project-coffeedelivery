package defix.coffeedelivery.services.account;

import defix.coffeedelivery.services.account.exceptions.*;
import defix.coffeedelivery.db.entity.Role;
import defix.coffeedelivery.db.entity.Account;
import defix.coffeedelivery.db.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Validator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountService implements UserDetailsService {

    public enum UpdateType {
        PASSWORD,
        PHONE_NUMBER,
        NAME,
        ALL
    }

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /* !!! PHONE NUMBER == USERNAME !!! */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> user = Optional.ofNullable(accountRepository.getAccountByPhoneNumber(username));

        if(user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return user.get();
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account findAccountById(int id) throws AccountDoNotFoundException {
        Optional<Account> account = accountRepository.findById(id);
        return account.orElseThrow(AccountDoNotFoundException::new);
    }

    public Account findAccountByPhoneNumber(String phoneNumber) throws AccountDoNotFoundException {
        Optional<Account> account = Optional
                .ofNullable(accountRepository.getAccountByPhoneNumber(phoneNumber));
        return account.orElseThrow(AccountDoNotFoundException::new);
    }

    public void saveAccount(RegisterAccountDTO accountData) throws AccountAlreadyExistsException {
        Optional<Account> account = Optional.ofNullable(accountRepository.getAccountByPhoneNumber(accountData.getPhoneNumber()));
        if(account.isPresent()) {
            throw new AccountAlreadyExistsException();
        }

        accountRepository.save(createNewAccountByDTO(accountData));
    }

    private Account createNewAccountByDTO(RegisterAccountDTO accountData) {
        Account account = new Account();
        account.setRoles(Collections.singleton(new Role(2, "ROLE_USER")));
        account.setPassword(passwordEncoder.encode(accountData.getPassword()));
        account.setName(accountData.getName());
        account.setUsername(accountData.getPhoneNumber());
        return account;
    }

    public void updateAccountData(ContactAccountDTO newAccountData, UpdateType updateType) {
        Account account = findAccountByPhoneNumber(newAccountData.getPhoneNumber());

        String newPhoneNumber = newAccountData.getPhoneNumber();
        String newPassword = passwordEncoder.encode(newAccountData.getPassword());
        String newName = newAccountData.getName();

        switch (updateType) {
            case PHONE_NUMBER -> account.setUsername(newPhoneNumber);
            case PASSWORD -> account.setPassword(newPassword);
            case NAME -> account.setName(newName);
            case ALL -> {
                account.setUsername(newPhoneNumber);
                account.setPassword(newPassword);
                account.setName(newName);
            }
        }

        accountRepository.save(account);
    }

    public void deleteAccount(int userId) throws AccountDoNotFoundException {
        if (accountRepository.findById(userId).isEmpty()) {
            throw new AccountDoNotFoundException();
        }
        accountRepository.deleteById(userId);
    }

    public AccountDTO getCurrentAccountDTO() {
        Account account = getCurrentAccount();
        return new AccountDTO(account.getId(),
                account.getName(),
                account.getUsername(),
                account
                        .getRoles()
                        .stream()
                        .map(item -> item.getAuthority())
                        .collect(Collectors.toSet()));
    }

    public Account getCurrentAccount() {
        String username = getAuthenticationUsername();
        return findAccountByPhoneNumber(username);
    }

    private String getAuthenticationUsername() throws UnauthorizedAccountException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!isAuthenticated()) {
            throw new UnauthorizedAccountException();
        }
        return ((UserDetails) authentication.getPrincipal()).getUsername();
    }

    public boolean isAuthenticated() {
        Optional<Authentication> authentication = Optional
                .ofNullable(SecurityContextHolder.getContext().getAuthentication());
        if (authentication.isEmpty() || !(authentication.get().getPrincipal() instanceof UserDetails)) {
            return false;
        }
        return true;
    }

    public void registerAccount(RegisterAccountDTO accountData)
            throws PasswordDoNotMatchException, AccountConstraintException {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<RegisterAccountDTO>> violations = validator.validate(accountData);

        if(!violations.isEmpty()) {
            throw new AccountConstraintException(violations);
        }

        if(!accountData.getPassword().equals(accountData.getConfirmPassword())) {
            throw new PasswordDoNotMatchException();
        }

        saveAccount(accountData);
    }
}
