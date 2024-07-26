package defix.coffeedelivery.services.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContactAccountDTO {
    private String phoneNumber;
    private String name;
    private String password;
}
