package defix.coffeedelivery.catalog.service.filter;

import lombok.Setter;

@Setter
public class PriceDefaultFilterProperty implements IProductFilterProperty<Integer> {

    private int minPrice = 0;
    private int maxPrice = Integer.MAX_VALUE;

    @Override
    public boolean isSatisfy(Integer checkProperty) {
        return checkProperty >= minPrice && checkProperty <= maxPrice;
    }
}
