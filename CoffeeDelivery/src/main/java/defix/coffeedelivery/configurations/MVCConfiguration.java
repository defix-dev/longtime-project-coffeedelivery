package defix.coffeedelivery.configurations;

import defix.coffeedelivery.confirmPage.RestartConditionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/remember_password").setViewName("remember_password");
        registry.addViewController("/current_orders").setViewName("current_orders");
        registry.addViewController("/order_history").setViewName("order_history");
        registry.addViewController("/instruction").setViewName("instruction");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RestartConditionInterceptor());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
