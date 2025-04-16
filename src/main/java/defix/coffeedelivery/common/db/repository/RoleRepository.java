package defix.coffeedelivery.common.db.repository;

import defix.coffeedelivery.common.db.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
