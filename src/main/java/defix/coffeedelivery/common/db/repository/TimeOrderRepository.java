package defix.coffeedelivery.common.db.repository;

import defix.coffeedelivery.common.db.entity.TimeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeOrderRepository extends JpaRepository<TimeOrder, Integer> {
}
