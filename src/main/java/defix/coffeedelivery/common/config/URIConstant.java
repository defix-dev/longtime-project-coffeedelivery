package defix.coffeedelivery.common.config;

import lombok.Getter;

@Getter
public enum URIConstant {
    HOME("home"),
    AUTH("authentication"),
    BASKET("basket"),
    CATALOG("catalog"),
    CONFIRM_ACTION("confirm_action"),
    EDIT_CONTACT_DATA("edit_contact_data"),
    ERROR_PAGE("error"),
    INSTRUCTION("instruction"),
    REMEMBER_PASSWORD("remember_password"),
    SUCCESS_LOGOUT("success_logout"),
    CURRENT_ORDERS("current_orders"),
    ORDER_HISTORY("order_history"),
    PAYMENT_PAGE("payment");

    private final String pageName;

    URIConstant(String pageName) {
        this.pageName = pageName;
    }
}
