package defix.coffeedelivery.common.db.repository;

import defix.coffeedelivery.common.db.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query("SELECT u FROM Account u WHERE u.username = :username")
    public Account getAccountByPhoneNumber(@Param("username") String phoneNumber);
}
