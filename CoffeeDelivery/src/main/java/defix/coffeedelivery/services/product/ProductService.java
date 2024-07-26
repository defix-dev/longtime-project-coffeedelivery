package defix.coffeedelivery.services.product;

import defix.coffeedelivery.services.product.exceptions.ProductDoNotFoundException;
import defix.coffeedelivery.db.entity.Product;
import defix.coffeedelivery.db.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {

    public enum ProductType {
        COFFEE_BEANS("Coffee Beans"),
        CHOCOLATE("Chocolate");

        private String fullName;

        private ProductType(String fullName) {
            this.fullName = fullName;
        }

        public String getFullName() {
            return fullName;
        }
    }

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product findProductById(int id) throws ProductDoNotFoundException {
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(ProductDoNotFoundException::new);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public static Map<Integer, ProductDTO> prepareProductsToResponse(List<Product> products) {
        Map<Integer, ProductDTO> outputProducts = new HashMap<>();

        for (Product product : products) {
            outputProducts.put(product.getId(),
                    new ProductDTO(product.getPrice(), product.getName(), product.getType()));
        }

        return outputProducts;
    }
}
