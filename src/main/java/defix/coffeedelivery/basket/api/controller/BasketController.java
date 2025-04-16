package defix.coffeedelivery.basket.api.controller;

import defix.coffeedelivery.common.config.URIConstant;
import defix.coffeedelivery.common.db.entity.Basket;
import defix.coffeedelivery.auth.service.AccountService;
import defix.coffeedelivery.basket.api.dto.response.BasketDTO;
import defix.coffeedelivery.basket.service.BasketService;
import defix.coffeedelivery.redirect.service.redirector.Redirect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Controller responsible for handling operations related to the user's basket,
 * including retrieving basket items, deleting them, and initiating the payment process.
 */
@Controller
@SessionAttributes("payment_dto")
public class BasketController {

    private final BasketService basketService;
    private final AccountService accountService;

    /**
     * Constructor for injecting {@link BasketService} and {@link AccountService}.
     *
     * @param basketService  the basket service
     * @param accountService the account service
     */
    @Autowired
    public BasketController(BasketService basketService, AccountService accountService) {
        this.basketService = basketService;
        this.accountService = accountService;
    }

    /**
     * Retrieves all basket items for the current authenticated user.
     *
     * @return {@link ResponseEntity} containing a set of {@link BasketDTO} objects
     */
    @GetMapping("/api/v1/baskets")
    @ResponseBody
    public ResponseEntity<Set<BasketDTO>> getBaskets() {
        return ResponseEntity.ok().body(BasketService
                .convertBasketToDTO(getAccountBaskets()));
    }

    /**
     * Returns the basket page view.
     *
     * @return a string representing the view name for the basket page
     */
    @GetMapping("/basket")
    public String getBasketPage() {
        return Redirect.changePage(URIConstant.BASKET);
    }

    /**
     * Deletes a specific basket item by its ID.
     *
     * @param id the ID of the basket item to delete
     * @return empty {@link ResponseEntity} with HTTP 200 OK if successful
     */
    @DeleteMapping("/api/v1/baskets/{id}")
    public ResponseEntity<Void> deleteBasket(@PathVariable("id") int id) {
        basketService.deleteBasket(id, getAccountBaskets());
        return ResponseEntity.ok().build();
    }

    /**
     * Initiates the payment process for a specific basket item.
     *
     * @param id    the ID of the basket item
     * @param model the Spring MVC model used to store session attributes
     * @return {@link ResponseEntity} indicating the result of the payment initiation
     */
    @PostMapping("/api/v1/baskets/{id}")
    public ResponseEntity<Void> toPayment(@PathVariable int id,
                                          Model model) {
        return basketService.toPayment(id, model);
    }

    /**
     * Helper method to get the basket items of the currently authenticated user.
     *
     * @return a set of {@link Basket} entities
     */
    private Set<Basket> getAccountBaskets() {
        return accountService.getCurrentAccount().getBaskets();
    }
}
