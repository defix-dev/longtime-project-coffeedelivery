package defix.coffeedelivery.basket.api.dto.response;

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
    private int productId;
    private String productName;
    private int productPrice;
}
