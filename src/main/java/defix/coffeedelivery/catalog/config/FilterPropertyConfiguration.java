package defix.coffeedelivery.catalog.config;

import defix.coffeedelivery.catalog.service.filter.IdDefaultFilterProperty;
import defix.coffeedelivery.catalog.service.filter.NameDefaultFilterProperty;
import defix.coffeedelivery.catalog.service.filter.PriceDefaultFilterProperty;
import defix.coffeedelivery.catalog.service.filter.TypeDefaultFilterProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class FilterPropertyConfiguration {

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public IdDefaultFilterProperty idDefaultFilterProperty() {
        return new IdDefaultFilterProperty();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public PriceDefaultFilterProperty priceDefaultFilterProperty() {
        return new PriceDefaultFilterProperty();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public NameDefaultFilterProperty nameDefaultFilterProperty() {
        return new NameDefaultFilterProperty();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public TypeDefaultFilterProperty typeDefaultFilterProperty() {
        return new TypeDefaultFilterProperty();
    }
}
