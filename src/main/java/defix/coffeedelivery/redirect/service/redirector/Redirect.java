package defix.coffeedelivery.redirect.service.redirector;

import defix.coffeedelivery.common.config.URIConstant;

import java.net.URI;

public class Redirect {
    public static String redirect(IRedirect redirect) {
        redirect.executeLogic();
        return redirect.redirect();
    }


    public static String changePage(URIConstant pageName) {
        return pageName.getPageName();
    }

    public static String buildRedirectUrl(String url) {
        return "redirect:/" + url;
    }

    public static URI getLocation(URIConstant targetUrl) {
        return URI.create("/" + targetUrl.getPageName());
    }
}
