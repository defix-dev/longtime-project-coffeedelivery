package defix.coffeedelivery.catalog.api.controller;

import defix.coffeedelivery.common.config.URIConstant;
import defix.coffeedelivery.catalog.service.CatalogService;
import defix.coffeedelivery.catalog.api.dto.response.ProductDTO;
import defix.coffeedelivery.catalog.service.ProductService;
import defix.coffeedelivery.redirect.service.redirector.Redirect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class CatalogController {
    private final CatalogService catalogService;

    @Autowired
    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/catalog")
    public String catalog() {
        return Redirect.changePage(URIConstant.CATALOG);
    }

    @ModelAttribute("product_types")
    public List<String> productTypes() {
        return
                Arrays.stream(ProductService.ProductType.values())
                        .map(ProductService.ProductType::getFullName).toList();
    }

    @PostMapping("/catalog/change_filter_type")
    public ResponseEntity<Void> changeFilterType(@RequestParam String type) {
        catalogService.changeFilterType(type);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/catalog/change_filter_minPrice")
    public ResponseEntity<Void> changeFilterMinPrice(@RequestParam int minPrice) {
        catalogService.changeFilterMinPrice(minPrice);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/catalog/change_filter_maxPrice")
    public ResponseEntity<Void> changeFilterMaxPrice(@RequestParam int maxPrice) {
        catalogService.changeFilterMaxPrice(maxPrice);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/search")
    @ResponseBody
    public ResponseEntity<Map<Integer, ProductDTO>> getSearchItems(@RequestParam("value") String searchValue) {
        return ResponseEntity.ok().body(catalogService.getSearchItems(searchValue));
    }

    @PostMapping("/catalog/add_to_basket")
    public ResponseEntity<Void> addProductToBasket(@RequestParam("id") int productId) {
        catalogService.addProductToBasket(productId);
        return ResponseEntity.ok().build();
    }
}