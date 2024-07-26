package defix.coffeedelivery.services.feedback;

import defix.coffeedelivery.services.IUniqueValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Feedback implements IUniqueValue<Integer> {
    private int id;
    private int accountId;
    private String feedback;

    @Override
    public Integer getUniqueKey() {
        return id;
    }
}
