package defix.coffeedelivery.db.repositories;

import defix.coffeedelivery.db.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
