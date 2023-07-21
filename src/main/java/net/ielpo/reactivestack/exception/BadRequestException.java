package net.ielpo.reactivestack.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Alberto Ielpo
 */
public class BadRequestException extends RuntimeException implements FluxException {

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
