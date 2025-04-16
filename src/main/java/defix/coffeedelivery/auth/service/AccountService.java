package defix.coffeedelivery.auth.service;

import defix.coffeedelivery.auth.api.dto.request.RegisterAccountDTO;
import defix.coffeedelivery.auth.exception.*;
import defix.coffeedelivery.contactData.api.dto.response.AccountDTO;
import defix.coffeedelivery.contactData.api.dto.request.ContactAccountDTO;
import defix.coffeedelivery.common.db.entity.Role;
import defix.coffeedelivery.common.db.entity.Account;
import defix.coffeedelivery.common.db.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
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

    public Account findAccountById(int id) throws AccountNotFoundException {
        Optional<Account> account = accountRepository.findById(id);
        return account.orElseThrow(AccountNotFoundException::new);
    }

    public Account findAccountByPhoneNumber(String phoneNumber) throws AccountNotFoundException {
        Optional<Account> account = Optional
                .ofNullable(accountRepository.getAccountByPhoneNumber(phoneNumber));
        return account.orElseThrow(AccountNotFoundException::new);
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

    public AccountDTO getCurrentAccountDTO() {
        Account account = getCurrentAccount();
        return new AccountDTO(account.getId(),
                account.getName(),
                account.getUsername(),
                account
                        .getRoles()
                        .stream()
                        .map(Role::getAuthority)
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
        return authentication.isPresent() && authentication.get().getPrincipal() instanceof UserDetails;
    }

    public void registerAccount(RegisterAccountDTO accountData)
            throws PasswordNotMatchException, AccountConstraintException {
        if(!accountData.getPassword().equals(accountData.getConfirmPassword())) {
            throw new PasswordNotMatchException();
        }

        saveAccount(accountData);
    }
}
