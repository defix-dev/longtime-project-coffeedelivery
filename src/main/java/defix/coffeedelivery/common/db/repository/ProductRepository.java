package defix.coffeedelivery.common.db.repository;

import defix.coffeedelivery.common.db.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
