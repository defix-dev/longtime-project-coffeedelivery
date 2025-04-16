package defix.coffeedelivery.catalog.service;

import defix.coffeedelivery.common.db.entity.Product;
import defix.coffeedelivery.catalog.service.filter.IProductFilterProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductFilterService {

    @Autowired
    @Qualifier("idFilterProperties")
    private List<IProductFilterProperty<Integer>> idFilterProperties;

    @Autowired
    @Qualifier("priceFilterProperties")
    private List<IProductFilterProperty<Integer>> priceFilterProperties;

    @Autowired
    @Qualifier("nameFilterProperties")
    private List<IProductFilterProperty<String>> nameFilterProperties;

    @Autowired
    @Qualifier("typeFilterProperties")
    private List<IProductFilterProperty<String>> typeFilterProperties;

    public boolean isSuite(Product product) {
        boolean idFilterResult = idFilterProperties.stream().allMatch(idFilterProperty -> idFilterProperty.isSatisfy(product.getId()));
        boolean priceFilterResult = priceFilterProperties.stream().allMatch(priceFilterProperty -> priceFilterProperty.isSatisfy(product.getPrice()));
        boolean nameFilterResult = nameFilterProperties.stream().allMatch(nameFilterProperty -> nameFilterProperty.isSatisfy(product.getName()));
        boolean typeFilterResult = typeFilterProperties.stream().allMatch(typeFilterProperty -> typeFilterProperty.isSatisfy(product.getType()));

        return idFilterResult && priceFilterResult && nameFilterResult && typeFilterResult;
    }
}

