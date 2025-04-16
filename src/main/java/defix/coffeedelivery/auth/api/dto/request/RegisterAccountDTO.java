package defix.coffeedelivery.auth.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

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
