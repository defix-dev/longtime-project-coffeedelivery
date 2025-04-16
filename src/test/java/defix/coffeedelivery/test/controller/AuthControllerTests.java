package defix.coffeedelivery.test.controller;

import defix.coffeedelivery.auth.api.dto.request.RegisterAccountDTO;
import defix.coffeedelivery.auth.exception.PasswordNotMatchException;
import defix.coffeedelivery.auth.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTests
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    @WithMockUser
    public void testCheckAccountStatus_Authorized() throws Exception {
        when(accountService.isAuthenticated()).thenReturn(true);
        mockMvc.perform(get("/authentication/check_account_status")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("true"));
    }

    @Test
    public void testCheckAccountStatusFailure_Unauthorized() throws Exception {
        when(accountService.isAuthenticated()).thenReturn(false);
        mockMvc.perform(get("/authentication/check_account_status")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("false"));
    }

    @ParameterizedTest
    @CsvSource({
            "1234567890, 1234567890, 1234567890",
            "12345678901234567890, 12345678901234567890, 12345678901234567890"
    })
    public void testRegister_Success(String phone, String password, String confirmPassword) throws Exception {
        if(!password.equals(confirmPassword)) doThrow(new PasswordNotMatchException()).when(accountService).registerAccount(any(RegisterAccountDTO.class));
        else doNothing().when(accountService).registerAccount(any(RegisterAccountDTO.class));

        String accountDataJson = String.format("""
                    {
                        "phoneNumber": "%s",
                        "name": "name",
                        "password": "%s",
                        "confirmPassword": "%s"
                    }
                """, phone, password, confirmPassword);

        mockMvc.perform(post("/authentication/register")
                .content(accountDataJson)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource({
            "123456789, 12345, 12345",
            "1234567890, 123456, 12345",
            "1234567890, 1234567890123456789012345678900, 1234567890123456789012345678900",
            "1234567890123456789012345678900, 12345, 12345",
    })
    public void testRegister_Failure(String phone, String password, String confirmPassword) throws Exception {
        if(!password.equals(confirmPassword)) doThrow(new PasswordNotMatchException()).when(accountService).registerAccount(any(RegisterAccountDTO.class));
        else doNothing().when(accountService).registerAccount(any(RegisterAccountDTO.class));

        String accountDataJson = String.format("""
                    {
                        "phoneNumber": "%s",
                        "name": "name",
                        "password": "%s",
                        "confirmPassword": "%s"
                    }
                """, phone, password, confirmPassword);

        mockMvc.perform(post("/authentication/register")
                        .content(accountDataJson)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }
}
