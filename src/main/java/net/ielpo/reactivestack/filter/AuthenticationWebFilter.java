package net.ielpo.reactivestack.filter;

import java.util.Base64;
import java.util.List;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import net.ielpo.reactivestack.exception.UnauthorizedRequestException;
import reactor.core.publisher.Mono;

/**
 * @author Alberto Ielpo
 * @apiNote simple authentication web filter
 *          TODO:
 *          https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html#use-aspectj
 */
@Component
@Order(FilterOrder.AUTHENTICATION)
public class AuthenticationWebFilter implements WebFilter {

    private String[] authenticatedController = { "/hello" };

    /*** allowed user - fixed for didactical purposes */
    private String username = "user";
    private String password = "user";

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange,
            WebFilterChain webFilterChain) {

        System.out.println("AuthenticationWebFilter called");

        ServerHttpRequest request = serverWebExchange.getRequest();
        HttpHeaders headers = serverWebExchange.getRequest().getHeaders();

        RequestPath rp = request.getPath();

        for (String authPath : authenticatedController) {
            if (rp.value().startsWith(authPath)) {
                List<String> auth = headers.getOrEmpty("Authorization");
                if (auth.size() == 0) {
                    throw new UnauthorizedRequestException("Not authorized");
                }

                String data = auth.get(0);
                String[] parts = data.split("Basic ");

                if (parts.length != 2) {
                    throw new UnauthorizedRequestException("Not authorized");
                }

                try {
                    if (!String.format("%s:%s", username, password)
                            .equals(new String(Base64.getDecoder().decode(parts[1])))) {
                        throw new UnauthorizedRequestException("Not authorized");
                    }

                } catch (Exception e) {
                    throw new UnauthorizedRequestException("Not authorized");
                }

            }
        }

        /** authorized... */
        return webFilterChain.filter(serverWebExchange);
    }
}