package defix.coffeedelivery.home.api.controller;

import defix.coffeedelivery.common.config.URIConstant;
import defix.coffeedelivery.home.api.dto.response.FeedbackDTO;
import defix.coffeedelivery.home.service.HomeService;
import defix.coffeedelivery.redirect.service.redirector.Redirect;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Controller
public class HomeController {
    private final HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @ModelAttribute("is_authenticated")
    public boolean isAuthenticated(HttpSession session) throws IOException, InterruptedException {
        return homeService.isAuthenticated(session);
    }

    @GetMapping({"","/"})
    public String redirectToHomePage(HttpSession session) {
        return homePage(session);
    }

    @GetMapping("/home")
    public String homePage(HttpSession session) {
        homeService.setupFeedbackOptions(session);
        return Redirect.changePage(URIConstant.HOME);
    }
}
