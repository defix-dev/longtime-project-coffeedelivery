package defix.coffeedelivery.confirmPage.RequestActions;

import java.net.URI;

public interface IRequestAction {
    URI getLocation();
    void executeRequest();
}
