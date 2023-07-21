package net.ielpo.reactivestack.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

/**
 * @author Alberto Ielpo
 */
@Component
@Order(FilterOrder.X_REACTIVE)
public class XReactiveWebFilter implements WebFilter {

    /**
     * @author Alberto Ielpo
     * @apiNote add a custom header to the response
     */
    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange,
            WebFilterChain webFilterChain) {

        System.out.println("XReactiveWebFilter called");

        serverWebExchange.getResponse().getHeaders().add("x-reactive", "ok");
        return webFilterChain.filter(serverWebExchange);
    }
}