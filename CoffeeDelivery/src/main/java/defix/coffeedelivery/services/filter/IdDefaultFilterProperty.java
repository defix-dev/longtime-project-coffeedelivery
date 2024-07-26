package defix.coffeedelivery.services.filter;

public class IdDefaultFilterProperty implements IProductFilterProperty<Integer> {
    @Override
    public boolean isSatisfy(Integer checkProperty) {
        return true;
    }
}
