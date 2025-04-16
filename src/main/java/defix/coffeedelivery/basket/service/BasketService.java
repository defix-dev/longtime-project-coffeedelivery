package defix.coffeedelivery.basket.service;

import defix.coffeedelivery.basket.api.dto.response.BasketDTO;
import defix.coffeedelivery.basket.exception.BasketNotFoundException;
import defix.coffeedelivery.basket.exception.BasketNotExistsOnAccountException;
import defix.coffeedelivery.common.config.URIConstant;
import defix.coffeedelivery.common.db.entity.Product;
import defix.coffeedelivery.common.db.entity.Basket;
import defix.coffeedelivery.common.db.repository.BasketRepository;
import defix.coffeedelivery.auth.service.AccountService;
import defix.coffeedelivery.payment.api.dto.request.PaymentDTO;
import defix.coffeedelivery.catalog.service.ProductService;
import defix.coffeedelivery.redirect.service.redirector.Redirect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;
import java.util.stream.Collectors;

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

    public Basket getBasket(Integer id) throws BasketNotFoundException {
        return basketRepository.findById(id).orElseThrow(BasketNotFoundException::new);
    }

    public void addProductToBasket(Integer productId) {
        Basket basket = new Basket();
        basket.setProduct(productService.findProductById(productId));
        basket.setAccount(accountService.getCurrentAccount());

        basketRepository.save(basket);
    }

    public static Set<BasketDTO> convertBasketToDTO(Set<Basket> baskets) {
        Set<BasketDTO> clientData = new TreeSet<>(new BasketIdComparator());

        for (Basket basket : baskets) {
            clientData.add(new BasketDTO(
                    basket.getId(),
                    basket.getProduct().getId(),
                    basket.getProduct().getName(),
                    basket.getProduct().getPrice()
            ));
        }

        return clientData;
    }

    public void deleteBasket(int id, Set<Basket> userBaskets) throws BasketNotExistsOnAccountException {
        Optional<Basket> basketToDelete = userBaskets.stream().filter(item -> item.getId() == id).findFirst();

        if(basketToDelete.isEmpty()) {
            throw new BasketNotExistsOnAccountException();
        }

        basketRepository.delete(basketToDelete.get());
    }

    public ResponseEntity<Void> toPayment(int id, Model model) {
        Product product = productService.findProductById(id);
        Set<Basket> baskets = accountService.getCurrentAccount()
                .getBaskets().stream().filter(
                        basket -> basket.getProduct().getId()
                                == id
                ).collect(Collectors.toSet());
        int count = baskets.size();
        int price = product.getPrice();
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setCount(count);
        paymentDTO.setName(product.getName());
        paymentDTO.setType(product.getType());
        paymentDTO.setPrice(price * count);
        paymentDTO.setProductId(id);

        model.addAttribute("payment_dto", paymentDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(Redirect.getLocation(URIConstant.PAYMENT_PAGE));
        return ResponseEntity.ok().headers(headers).build();
    }
}
