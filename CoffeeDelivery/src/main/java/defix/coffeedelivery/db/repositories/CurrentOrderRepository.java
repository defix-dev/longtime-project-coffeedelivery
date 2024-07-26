package defix.coffeedelivery.db.repositories;

import defix.coffeedelivery.db.entity.CurrentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrentOrderRepository extends JpaRepository<CurrentOrder, Integer> {
}
