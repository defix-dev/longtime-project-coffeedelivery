package defix.coffeedelivery.common.db.repository;

import defix.coffeedelivery.common.db.entity.CurrentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrentOrderRepository extends JpaRepository<CurrentOrder, Integer> {
}
