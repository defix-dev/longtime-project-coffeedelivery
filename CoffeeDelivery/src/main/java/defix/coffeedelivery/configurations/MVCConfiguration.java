package defix.coffeedelivery.configurations;

import defix.coffeedelivery.confirmPage.ConfirmActionController;
import defix.coffeedelivery.paymentPage.PaymentController;
import defix.coffeedelivery.services.interceptors.restartCondition.RestartConditionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfiguration implements WebMvcConfigurer {
    private final ConfirmActionController confirmActionController;
    private final PaymentController paymentController;

    @Autowired
    public MVCConfiguration(ConfirmActionController confirmActionController, PaymentController paymentController) {
        this.confirmActionController = confirmActionController;
        this.paymentController = paymentController;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/remember_password").setViewName(URLConstant.REMEMBER_PASSWORD.getPageName());
        registry.addViewController("/current_orders").setViewName(URLConstant.CURRENT_ORDERS.getPageName());
        registry.addViewController("/order_history").setViewName(URLConstant.ORDER_HISTORY.getPageName());
        registry.addViewController("/instruction").setViewName(URLConstant.INSTRUCTION.getPageName());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RestartConditionInterceptor(
                confirmActionController,
                paymentController));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8888")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
