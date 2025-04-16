package defix.coffeedelivery.common.config;

import defix.coffeedelivery.confirm.api.controller.ConfirmActionController;
import defix.coffeedelivery.payment.api.controller.PaymentController;
import defix.coffeedelivery.confirm.interceptor.RestartConditionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        registry.addViewController("/remember_password").setViewName(URIConstant.REMEMBER_PASSWORD.getPageName());
        registry.addViewController("/current_orders").setViewName(URIConstant.CURRENT_ORDERS.getPageName());
        registry.addViewController("/order_history").setViewName(URIConstant.ORDER_HISTORY.getPageName());
        registry.addViewController("/instruction").setViewName(URIConstant.INSTRUCTION.getPageName());
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
