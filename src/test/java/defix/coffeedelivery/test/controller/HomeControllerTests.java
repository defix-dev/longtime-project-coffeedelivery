package defix.coffeedelivery.test.controller;

import defix.coffeedelivery.common.config.URIConstant;
import defix.coffeedelivery.home.api.dto.response.FeedbackDTO;
import defix.coffeedelivery.home.service.HomeService;
import defix.coffeedelivery.test.controller.ControllerTests;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ControllerTests
class HomeControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HomeService homeService;

    @MockBean
    private HttpSession session;

    @Test
    @WithMockUser
    void testHomePage_ReturnsView() throws Exception {
        Mockito.doNothing().when(homeService).setupFeedbackOptions(any());

        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name(URIConstant.HOME.getPageName()));
    }

    @Test
    @WithMockUser
    void testLoadFeedbacks_ReturnsJson() throws Exception {
        LinkedHashMap<String, FeedbackDTO> mockFeedbacks = new LinkedHashMap<>();
        mockFeedbacks.put("1", new FeedbackDTO("Great coffee!", "user1"));
        Mockito.when(homeService.loadFeedbacks(any())).thenReturn(mockFeedbacks);

        mockMvc.perform(get("/api/load_feedbacks"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.1.feedback").value("Great coffee!"))
                .andExpect(jsonPath("$.1.clientName").value("user1"));
    }

    @Test
    @WithMockUser
    void testPostFeedback_ReturnsOk() throws Exception {
        Mockito.doNothing().when(homeService).postFeedback("Awesome!");

        mockMvc.perform(post("/home/post_feedback")
                        .param("feedback", "Awesome!")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());

        verify(homeService, times(1)).postFeedback("Awesome!");
    }
}
