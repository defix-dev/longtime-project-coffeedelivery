package defix.coffeedelivery.test.controller;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Tag("Controller")
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerTests {
}
