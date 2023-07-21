package net.ielpo.reactivestack.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Alberto Ielpo
 */
public class ForbiddenRequestException extends RuntimeException implements FluxException {
    public ForbiddenRequestException() {
        super();
    }

    public ForbiddenRequestException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
