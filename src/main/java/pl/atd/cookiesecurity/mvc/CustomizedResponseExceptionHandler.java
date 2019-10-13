package pl.atd.cookiesecurity.mvc;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomizedResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PreAuthenticatedCredentialsNotFoundException.class)
    protected ResponseEntity<Object> handleNotAuthorized(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, "No authorized user found", new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }
}