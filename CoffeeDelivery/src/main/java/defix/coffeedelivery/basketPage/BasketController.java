package defix.coffeedelivery.basketPage;

import defix.coffeedelivery.configurations.URLConstant;
import defix.coffeedelivery.db.entity.Basket;
import defix.coffeedelivery.services.account.AccountService;
import defix.coffeedelivery.services.basket.BasketDTO;
import defix.coffeedelivery.services.basket.BasketService;
import defix.coffeedelivery.services.redirectors.Redirect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
public class BasketController {
    private final BasketService basketService;
    private final AccountService accountService;

    @Autowired
    public BasketController(BasketService basketService, AccountService accountService) {
        this.basketService = basketService;
        this.accountService = accountService;
    }

    @GetMapping("/api/get_baskets")
    @ResponseBody
    public ResponseEntity<Set<BasketDTO>> getBaskets() {
        return ResponseEntity.ok().body(basketService
                .convertBasketToDTO(getAccountBaskets()));
    }

    @GetMapping("/basket")
    public String getBasketPage() {
        return Redirect.changePage(URLConstant.BASKET);
    }

    @DeleteMapping("basket/delete")
    public ResponseEntity<Void> deleteBasket(@RequestParam("id") int basketId) {
            basketService.deleteBasket(basketId, getAccountBaskets());
            return ResponseEntity.ok().build();
    }

    private Set<Basket> getAccountBaskets() {
        return accountService.getCurrentAccount().getBaskets();
    }
}
