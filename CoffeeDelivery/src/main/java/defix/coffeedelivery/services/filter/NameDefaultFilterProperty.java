package defix.coffeedelivery.services.filter;

import lombok.Setter;

@Setter
public class NameDefaultFilterProperty implements IProductFilterProperty<String> {

    private String name = "";

    @Override
    public boolean isSatisfy(String checkProperty) {
        return checkProperty.contains(name);
    }
}
