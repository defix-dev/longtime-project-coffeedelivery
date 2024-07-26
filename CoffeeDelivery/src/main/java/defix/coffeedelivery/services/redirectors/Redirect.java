package defix.coffeedelivery.services.redirectors;

import defix.coffeedelivery.configurations.URLConstant;

import java.net.URI;

public class Redirect {
    public static String redirect(IRedirect redirect) {
        redirect.executeLogic();
        return redirect.redirect();
    }

    public static String defaultRedirect(String targetUrl) {
        return buildRedirectUrl(targetUrl);
    }

    public static String defaultRedirect(URLConstant targetUrl) {
        return buildRedirectUrl(targetUrl.getPageName());
    }

    public static String changePage(URLConstant pageName) {
        return pageName.getPageName();
    }

    public static String buildRedirectUrl(String url) {
        return "redirect:/" + url;
    }

    public static URI getLocation(URLConstant targetUrl) {
        return URI.create("/" + targetUrl.getPageName());
    }
}
