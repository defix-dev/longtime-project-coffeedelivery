package defix.coffeedelivery.services.basket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasketDTO {
    private int id;
    private String productName;
    private int productPrice;
    private int count;
}
