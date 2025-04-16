package defix.coffeedelivery.catalog.config;

import defix.coffeedelivery.catalog.service.filter.IProductFilterProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration
public class ProductFilterConfiguration {
    private final IProductFilterProperty<Integer> idDefaultFilterProperty;
    private final IProductFilterProperty<Integer> priceDefaultFilterProperty;
    private final IProductFilterProperty<String> nameDefaultFilterProperty;
    private final IProductFilterProperty<String> typeDefaultFilterProperty;

    @Autowired
    public ProductFilterConfiguration(IProductFilterProperty<Integer> idDefaultFilterProperty,
                                      IProductFilterProperty<Integer> priceDefaultFilterProperty,
                                      IProductFilterProperty<String> nameDefaultFilterProperty,
                                      IProductFilterProperty<String> typeDefaultFilterProperty) {
        this.idDefaultFilterProperty = idDefaultFilterProperty;
        this.priceDefaultFilterProperty = priceDefaultFilterProperty;
        this.nameDefaultFilterProperty = nameDefaultFilterProperty;
        this.typeDefaultFilterProperty = typeDefaultFilterProperty;
    }

    @Bean("idFilterProperties")
    public List<IProductFilterProperty<Integer>> idFilterProperties() {
        return Collections.singletonList(idDefaultFilterProperty);
    }

    @Bean("priceFilterProperties")
    public List<IProductFilterProperty<Integer>> priceFilterProperties() {
        return Collections.singletonList(priceDefaultFilterProperty);
    }

    @Bean("nameFilterProperties")
    public List<IProductFilterProperty<String>> nameFilterProperties() {
        return Collections.singletonList(nameDefaultFilterProperty);
    }

    @Bean("typeFilterProperties")
    public List<IProductFilterProperty<String>> typeFilterProperties() {
        return Collections.singletonList(typeDefaultFilterProperty);
    }
}
