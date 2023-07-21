package net.ielpo.reactivestack.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Alberto Ielpo
 */
public class InternalServerErrorException extends RuntimeException implements FluxException {

    public InternalServerErrorException() {
        super();
    }

    public InternalServerErrorException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
