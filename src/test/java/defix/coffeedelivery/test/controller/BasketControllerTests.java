package defix.coffeedelivery.test.controller;

import defix.coffeedelivery.auth.service.AccountService;
import defix.coffeedelivery.basket.api.dto.response.BasketDTO;
import defix.coffeedelivery.basket.service.BasketService;
import defix.coffeedelivery.common.db.entity.Account;
import defix.coffeedelivery.common.db.entity.Basket;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BasketControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BasketService basketService;

    @MockBean
    private AccountService accountService;

    @Test
    @WithMockUser
    public void testGetBaskets() throws Exception {
        Set<Basket> baskets = new HashSet<>();
        Account account = new Account();
        account.setBaskets(baskets);

        when(accountService.getCurrentAccount()).thenReturn(account);
        try (MockedStatic<BasketService> mockedStatic = mockStatic(BasketService.class)) {
            mockedStatic.when(() -> BasketService.convertBasketToDTO(baskets))
                    .thenReturn(Collections.emptySet());

            mockMvc.perform(get("/api/v1/baskets"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"));
        }
    }

    @Test
    @WithMockUser
    public void testDeleteBasket() throws Exception {
        Set<Basket> baskets = new HashSet<>();
        Account account = new Account();
        account.setBaskets(baskets);

        when(accountService.getCurrentAccount()).thenReturn(account);
        doNothing().when(basketService).deleteBasket(eq(1), eq(baskets));

        mockMvc.perform(delete("/api/v1/baskets/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testToPayment() throws Exception {
        when(basketService.toPayment(anyInt(), any())).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(post("/api/v1/baskets/1"))
                .andExpect(status().isOk());
    }
}
