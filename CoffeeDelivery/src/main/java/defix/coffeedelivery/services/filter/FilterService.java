package defix.coffeedelivery.services.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilterService {
    private final PriceDefaultFilterProperty priceDefaultFilterProperty;
    private final NameDefaultFilterProperty nameDefaultFilterProperty;
    private final TypeDefaultFilterProperty typeDefaultFilterProperty;

    @Autowired
    public FilterService(PriceDefaultFilterProperty priceDefaultFilterProperty, NameDefaultFilterProperty nameDefaultFilterProperty, TypeDefaultFilterProperty typeDefaultFilterProperty) {
        this.priceDefaultFilterProperty = priceDefaultFilterProperty;
        this.nameDefaultFilterProperty = nameDefaultFilterProperty;
        this.typeDefaultFilterProperty = typeDefaultFilterProperty;
    }

    public void changeType(final String type) {
        typeDefaultFilterProperty.setType(type);
    }

    public void changeMinPrice(final Integer minPrice) {
        priceDefaultFilterProperty.setMinPrice(minPrice);
    }

    public void changeMaxPrice(final Integer maxPrice) {
        priceDefaultFilterProperty.setMaxPrice(maxPrice);
    }

    public void changeQueryName(final String queryName) {
        nameDefaultFilterProperty.setName(queryName);
    }
}
