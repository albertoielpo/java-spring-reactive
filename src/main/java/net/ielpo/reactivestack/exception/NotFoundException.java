package net.ielpo.reactivestack.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Alberto Ielpo
 */
public class NotFoundException extends RuntimeException implements FluxException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
