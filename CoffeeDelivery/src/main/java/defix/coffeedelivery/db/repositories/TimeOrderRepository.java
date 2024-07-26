package defix.coffeedelivery.db.repositories;

import defix.coffeedelivery.db.entity.TimeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeOrderRepository extends JpaRepository<TimeOrder, Integer> {
}
