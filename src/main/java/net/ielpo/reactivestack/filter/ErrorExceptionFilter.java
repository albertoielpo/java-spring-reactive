package net.ielpo.reactivestack.filter;

import java.util.HashMap;
import java.util.Map;

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

import net.ielpo.reactivestack.exception.FluxException;
import reactor.core.publisher.Mono;

/**
 * @author Alberto Ielpo
 */
@Component
@Order(FilterOrder.ERROR_EXCEPTION)
public class ErrorExceptionFilter extends AbstractErrorWebExceptionHandler {

    public ErrorExceptionFilter(ErrorAttributes errorAttributes, Resources resources,
            ApplicationContext applicationContext, ServerCodecConfigurer configurer) {
        super(errorAttributes, resources, applicationContext);
        this.setMessageWriters(configurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {

        Throwable error = getError(request);

        HttpStatus httpStatus = (error instanceof FluxException) ? ((FluxException) error).getHttpStatus()
                : HttpStatus.INTERNAL_SERVER_ERROR;

        Map<String, Object> errorPropertiesMap = new HashMap<>() {
            {
                this.put("status", "ERROR");
                this.put("details", error.getMessage());
                this.put("timestamp", System.currentTimeMillis());
            }
        };

        return ServerResponse
                .status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(errorPropertiesMap));
    }
}