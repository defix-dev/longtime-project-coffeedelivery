package defix.coffeedelivery.services.filter;

public interface IProductFilterProperty<T> {
    boolean isSatisfy(T checkProperty);
}
