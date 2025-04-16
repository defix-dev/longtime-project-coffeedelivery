package defix.coffeedelivery.catalog.api.controller;

import defix.coffeedelivery.common.config.URIConstant;
import defix.coffeedelivery.catalog.service.CatalogService;
import defix.coffeedelivery.catalog.api.dto.response.ProductDTO;
import defix.coffeedelivery.catalog.service.ProductService;
import defix.coffeedelivery.redirect.service.redirector.Redirect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * REST controller responsible for managing catalog-related operations, including product filtering,
 * searching, and adding products to the basket.
 */
@RestController
public class CatalogController {

    private final CatalogService catalogService;

    /**
     * Constructor for injecting the {@link CatalogService}.
     *
     * @param catalogService the catalog service to be injected
     */
    @Autowired
    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    /**
     * Returns the catalog page as an HTML response.
     *
     * @return HTML content of the catalog page
     */
    @GetMapping(path = "/catalog", produces = MediaType.TEXT_HTML_VALUE)
    public String catalog() {
        return Redirect.changePage(URIConstant.CATALOG);
    }

    /**
     * Provides a list of product types for use in the frontend filtering UI.
     *
     * @return a list of product type names
     */
    @ModelAttribute("product_types")
    public List<String> productTypes() {
        return
                Arrays.stream(ProductService.ProductType.values())
                        .map(ProductService.ProductType::getFullName)
                        .toList();
    }

    /**
     * Changes the selected product filter type.
     *
     * @param type the new product filter type to be applied
     * @return empty {@link ResponseEntity} with status 200 OK if successful
     */
    @PostMapping("/api/v1/catalog/change_filter_type")
    public ResponseEntity<Void> changeFilterType(@RequestParam String type) {
        catalogService.changeFilterType(type);
        return ResponseEntity.ok().build();
    }

    /**
     * Changes the minimum price filter for the catalog.
     *
     * @param minPrice the new minimum price for the filter
     * @return empty {@link ResponseEntity} with status 200 OK if successful
     */
    @PostMapping("/api/v1/catalog/change_filter_minPrice")
    public ResponseEntity<Void> changeFilterMinPrice(@RequestParam int minPrice) {
        catalogService.changeFilterMinPrice(minPrice);
        return ResponseEntity.ok().build();
    }

    /**
     * Changes the maximum price filter for the catalog.
     *
     * @param maxPrice the new maximum price for the filter
     * @return empty {@link ResponseEntity} with status 200 OK if successful
     */
    @PostMapping("/api/v1/catalog/change_filter_maxPrice")
    public ResponseEntity<Void> changeFilterMaxPrice(@RequestParam int maxPrice) {
        catalogService.changeFilterMaxPrice(maxPrice);
        return ResponseEntity.ok().build();
    }

    /**
     * Searches for products in the catalog based on the provided search value.
     *
     * @param searchValue the search string to filter products
     * @return {@link ResponseEntity} containing a map of product IDs to {@link ProductDTO} objects
     */
    @GetMapping("/api/v1/catalog/search")
    public ResponseEntity<Map<Integer, ProductDTO>> getSearchItems(@RequestParam("value") String searchValue) {
        return ResponseEntity.ok().body(catalogService.getSearchItems(searchValue));
    }

    /**
     * Adds a product to the current user's basket.
     *
     * @param productId the ID of the product to be added to the basket
     * @return empty {@link ResponseEntity} with status 200 OK if successful
     */
    @PostMapping("/api/v1/catalog")
    public ResponseEntity<Void> addProductToBasket(@RequestParam("id") int productId) {
        catalogService.addProductToBasket(productId);
        return ResponseEntity.ok().build();
    }
}
