package defix.coffeedelivery.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import defix.coffeedelivery.auth.service.AccountService;
import defix.coffeedelivery.common.config.URIConstant;
import defix.coffeedelivery.contactData.api.dto.request.ContactAccountDTO;
import defix.coffeedelivery.contactData.api.dto.response.AccountDTO;
import defix.coffeedelivery.test.controller.ControllerTests;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ControllerTests
class EditContactDataControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void testEditControllerData_ReturnsRedirect() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        Mockito.when(accountService.getCurrentAccountDTO()).thenReturn(accountDTO);

        mockMvc.perform(get("/edit_contact_data"))
                .andExpect(status().isOk())
                .andExpect(view().name(containsString(URIConstant.EDIT_CONTACT_DATA.getPageName())));
    }

    @Test
    @WithMockUser
    void testSaveChanges_SetsModelAttributeAndRedirects() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        Mockito.when(accountService.getCurrentAccountDTO()).thenReturn(accountDTO);

        ContactAccountDTO contactAccountDTO = new ContactAccountDTO();

        mockMvc.perform(put("/api/v1/users/me/contact_data")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(contactAccountDTO)))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.LOCATION, "/" + URIConstant.CONFIRM_ACTION.getPageName()));
    }
}
