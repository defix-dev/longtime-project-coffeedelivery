package defix.coffeedelivery.catalog.service.filter;

import lombok.Setter;

@Setter
public class TypeDefaultFilterProperty implements IProductFilterProperty<String> {

    private String type = "";

    @Override
    public boolean isSatisfy(String checkProperty) {
        return type.isEmpty() ? true :
                checkProperty.toLowerCase().equals(type.toLowerCase());
    }
}
