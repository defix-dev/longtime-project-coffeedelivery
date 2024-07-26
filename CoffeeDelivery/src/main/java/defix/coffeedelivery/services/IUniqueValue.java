package defix.coffeedelivery.services;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface IUniqueValue<T> {
    @JsonIgnore
    T getUniqueKey();
}
