package pl.atd.cookiesecurity.security;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class CookieAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

    private final static String PRINCIPAL_REQUEST_COOKIE = "SM_USER";
    private final static String CREDENTIALS_REQUEST_COOKIE = "SM_GROUPS";

    /**
     * find principal from the cookie
     * @param request the request
     * @return string with user name
     */
    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals(PRINCIPAL_REQUEST_COOKIE)).map(Cookie::getValue).findFirst().orElseThrow(
                () -> new PreAuthenticatedCredentialsNotFoundException(PRINCIPAL_REQUEST_COOKIE + " cookie found in request.")
        );
    }

    /**
     * find credentials - therse are not available
     * @param request the request
     * @return null
     */
    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return null;
    }
}
