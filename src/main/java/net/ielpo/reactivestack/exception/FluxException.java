package net.ielpo.reactivestack.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Alberto Ielpo
 */
public interface FluxException {

    public HttpStatus getHttpStatus();

}
