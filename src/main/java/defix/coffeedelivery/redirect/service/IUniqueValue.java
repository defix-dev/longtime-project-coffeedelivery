package defix.coffeedelivery.redirect.service;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface IUniqueValue<T> {
    @JsonIgnore
    T getUniqueKey();
}
