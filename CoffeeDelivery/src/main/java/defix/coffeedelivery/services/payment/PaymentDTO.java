package defix.coffeedelivery.services.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentDTO {
    private int productId;
    private int price;
    private int count;
    private String name;
    private String type;
}
