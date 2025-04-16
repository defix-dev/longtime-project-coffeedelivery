package defix.coffeedelivery.catalog.service.filter;

public interface IProductFilterProperty<T> {
    boolean isSatisfy(T checkProperty);
}
