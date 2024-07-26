package defix.coffeedelivery.services.filter;

import defix.coffeedelivery.db.entity.Product;
import defix.coffeedelivery.services.filter.exceptions.ProductsDoNotFoundException;
import defix.coffeedelivery.services.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {
    private final ProductService productService;
    private final ProductFilterService productFilterService;

    @Autowired
    public SearchService(ProductService productService, ProductFilterService productFilterService) {
        this.productService = productService;
        this.productFilterService = productFilterService;
    }

    public List<Product> search() throws ProductsDoNotFoundException {
        List<Product> products = productService.findAll();
        List<Product> filteredProducts = new ArrayList<>();

        for (Product product : products) {
            if(productFilterService.isSuite(product)) {
                filteredProducts.add(product);
            }
        }

        if(filteredProducts.isEmpty()) {
            throw new ProductsDoNotFoundException();
        }

        return filteredProducts;
    }
}
