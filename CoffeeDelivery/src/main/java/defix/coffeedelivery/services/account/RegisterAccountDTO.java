package defix.coffeedelivery.services.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterAccountDTO {
    @NotBlank(message = "Phone Number is required")
    @Size(min = 10, max = 30)
    private String phoneNumber;

    private String name;

    @NotBlank(message = "Password is required")
    @Size(min = 5, max = 30)
    private String password;

    @NotBlank(message = "Confirm password is required")
    @Size(min = 5, max = 30)
    private String confirmPassword;
}
