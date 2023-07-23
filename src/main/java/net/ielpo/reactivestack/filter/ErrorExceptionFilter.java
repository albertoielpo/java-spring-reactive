package net.ielpo.reactivestack.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import net.ielpo.reactivestack.dto.BaseErr;
import net.ielpo.reactivestack.exception.FluxException;
import reactor.core.publisher.Mono;

/**
 * @author Alberto Ielpo
 */
@Component
@Order(FilterOrder.ERROR_EXCEPTION)
public class ErrorExceptionFilter extends AbstractErrorWebExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ErrorExceptionFilter.class);

    /**
     * Exception filter - catch exception using @Order priority
     * 
     * @param errorAttributes
     * @param resources
     * @param applicationContext
     * @param configurer
     */
    public ErrorExceptionFilter(ErrorAttributes errorAttributes, Resources resources,
            ApplicationContext applicationContext, ServerCodecConfigurer configurer) {
        super(errorAttributes, resources, applicationContext);
        this.setMessageWriters(configurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    /**
     * Return error payload
     * 
     * @param request
     * @return
     */
    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {

        Throwable error = getError(request);

        HttpStatus httpStatus = (error instanceof FluxException) ? ((FluxException) error).getHttpStatus()
                : HttpStatus.INTERNAL_SERVER_ERROR;

        switch (httpStatus) {
            case BAD_REQUEST:
            case UNAUTHORIZED:
            case FORBIDDEN:
            case NOT_FOUND:
            case METHOD_NOT_ALLOWED:
                logger.warn(String.format("HttpStatus: %s", httpStatus), error);
                break;
            default:
                logger.error(String.format("HttpStatus: %s", httpStatus), error);
        }

        return ServerResponse
                .status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new BaseErr(error.getMessage())));
    }
}