package defix.coffeedelivery.confirm.service.requestAction;

import java.net.URI;

public interface IRequestAction {
    URI getLocation();
    void executeRequest();
}
