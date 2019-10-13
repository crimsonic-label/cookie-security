package pl.atd.cookiesecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

/**
 * security configuration
 *
 * Check an example for pre authorization:
 * https://www.roytuts.com/spring-security-pre-authentication-example/
 *
 * Exception handling:
 * https://www.devglan.com/spring-security/exception-handling-in-spring-security
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Exception handling
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Autowired
    private AuthenticationEntryPoint restAuthenticationEntryPoint;

    /**
     * Security configuration:
     * all requests authenticated
     * filter for cookie pre authorization
     * @param http http security
     * @throws Exception any exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .addFilterAfter(cookieFilter(), RequestHeaderAuthenticationFilter.class);
    }

    /**
     * pre authorization filter bean (used in configuration)
     * @return the filter
     * @throws Exception any exception
     */
    @Bean(name = "cookieFilter")
    public CookieAuthenticationFilter cookieFilter() throws Exception {
        CookieAuthenticationFilter cookieAuthenticationFilter = new CookieAuthenticationFilter();
        // use authentication manager that sets authentication as authenticated
        cookieAuthenticationFilter.setAuthenticationManager(authenticationManager());
        return cookieAuthenticationFilter;
    }

    /**
     * creates Authentication manager that sets the authentication data as authenticated
     * @return authentication manager
     * @throws Exception andy exception
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                if(authentication.getPrincipal()!=null) {
                    // copy the authentication data and set it as authenticated
                    Authentication authenticated = new PreAuthenticatedAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials());
                    authentication.setAuthenticated(true);
                    return authenticated;
                }
                return authentication;
            }
        };
    }
}
