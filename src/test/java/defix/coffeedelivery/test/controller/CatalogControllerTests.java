package defix.coffeedelivery.test.controller;

import defix.coffeedelivery.catalog.service.CatalogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@ControllerTests
public class CatalogControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatalogService catalogService;

    @Test
    @WithMockUser
    public void testChangeFilterType() throws Exception {
        mockMvc.perform(post("/catalog/change_filter_type")
                .param("type", "Chocolate"))
                .andExpect(status().isOk());

        verify(catalogService, times(1)).changeFilterType("Chocolate");
    }

    @Test
    @WithMockUser
    public void testChangeFilterMinPrice() throws Exception {
        mockMvc.perform(post("/catalog/change_filter_minPrice")
                        .param("minPrice", "150"))
                .andExpect(status().isOk());

        verify(catalogService, times(1)).changeFilterMinPrice(150);
    }

    @Test
    @WithMockUser
    public void testChangeFilterMaxPrice() throws Exception {
        mockMvc.perform(post("/catalog/change_filter_maxPrice")
                        .param("maxPrice", "150"))
                .andExpect(status().isOk());

        verify(catalogService, times(1)).changeFilterMaxPrice(150);
    }

    @Test
    @WithMockUser
    public void testAddToBasket() throws Exception {
        mockMvc.perform(post("/catalog/add_to_basket")
                        .param("id", "1"))
                .andExpect(status().isOk());

        verify(catalogService, times(1)).addProductToBasket(1);
    }
}

