package defix.coffeedelivery.catalogPage;

import defix.coffeedelivery.configurations.URLConstant;
import defix.coffeedelivery.services.catalog.CatalogService;
import defix.coffeedelivery.services.product.ProductDTO;
import defix.coffeedelivery.services.product.ProductService;
import defix.coffeedelivery.services.redirectors.Redirect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/catalog")
public class CatalogController {
    private final CatalogService catalogService;

    @Autowired
    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping
    public String catalog() {
        return Redirect.changePage(URLConstant.CATALOG);
    }

    @ModelAttribute("product_types")
    public List<String> productTypes() {
        return
                Arrays.stream(ProductService.ProductType.values())
                        .map(value -> value.getFullName()).toList();
    }

    @PostMapping("/change_filter_type")
    public ResponseEntity<Void> changeFilterType(@RequestParam String type) {
        catalogService.changeFilterType(type);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/change_filter_minPrice")
    public ResponseEntity<Void> changeFilterMinPrice(@RequestParam int minPrice) {
        catalogService.changeFilterMinPrice(minPrice);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/change_filter_maxPrice")
    public ResponseEntity<Void> changeFilterMaxPrice(@RequestParam int maxPrice) {
        catalogService.changeFilterMaxPrice(maxPrice);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<Map<Integer, ProductDTO>> getSearchItems(@RequestParam("value") String searchValue) {
        return ResponseEntity.ok().body(catalogService.getSearchItems(searchValue));
    }

    @PostMapping("/add_to_basket")
    public ResponseEntity<Void> addProductToBasket(@RequestParam("id") int productId) {
        catalogService.addProductToBasket(productId);
        return ResponseEntity.ok().build();
    }
}