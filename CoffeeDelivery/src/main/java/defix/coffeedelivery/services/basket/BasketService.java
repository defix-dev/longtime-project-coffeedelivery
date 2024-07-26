package defix.coffeedelivery.services.basket;

import defix.coffeedelivery.services.basket.exceptions.BasketDoNotFound;
import defix.coffeedelivery.services.basket.exceptions.BasketIsNotExistsOnAccountException;
import defix.coffeedelivery.db.entity.Basket;
import defix.coffeedelivery.db.repositories.BasketRepository;
import defix.coffeedelivery.services.account.AccountService;
import defix.coffeedelivery.services.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BasketService {
    private final BasketRepository basketRepository;
    private final ProductService productService;
    private final AccountService accountService;

    @Autowired
    public BasketService(BasketRepository basketRepository, ProductService productService,
                         AccountService accountService) {
        this.basketRepository = basketRepository;
        this.productService = productService;
        this.accountService = accountService;
    }

    public Basket getBasket(Integer id) throws BasketDoNotFound {
        return basketRepository.findById(id).orElseThrow(BasketDoNotFound::new);
    }

    public void addProductToBasket(Integer productId) {
        Basket basket = new Basket();
        basket.setProduct(productService.findProductById(productId));
        basket.setAccount(accountService.getCurrentAccount());

        basketRepository.save(basket);
    }

    public Set<BasketDTO> convertBasketToDTO(Set<Basket> baskets) {
        Set<BasketDTO> clientData = new TreeSet<>(new BasketIdComparator());

        for (Basket basket : baskets) {
            clientData.add(new BasketDTO(
                    basket.getId(),
                    basket.getProduct().getName(),
                    basket.getProduct().getPrice(),
                    1
            ));
        }

        return clientData;
    }

    public void deleteBasket(int id, Set<Basket> userBaskets) throws BasketIsNotExistsOnAccountException {
        Optional<Basket> basketToDelete = userBaskets.stream().filter(item -> item.getId() == id).findFirst();

        if(basketToDelete.isEmpty()) {
            throw new BasketIsNotExistsOnAccountException();
        }

        basketRepository.delete(basketToDelete.get());
    }
}
