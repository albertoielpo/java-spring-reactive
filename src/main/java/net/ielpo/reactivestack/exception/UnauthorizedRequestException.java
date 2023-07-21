package net.ielpo.reactivestack.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Alberto Ielpo
 */
public class UnauthorizedRequestException extends RuntimeException implements FluxException {

    public UnauthorizedRequestException() {
        super();
    }

    public UnauthorizedRequestException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
