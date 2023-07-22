package net.ielpo.reactivestack.filter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import net.ielpo.reactivestack.config.Const;
import net.ielpo.reactivestack.manager.JwtTokenManager;
import reactor.core.publisher.Mono;

/**
 * @author Alberto Ielpo
 */
@Component
public class JwtTokenAuthConverter implements ServerAuthenticationConverter {

    @Autowired
    JwtTokenManager jwtTokenManager;

    @Autowired
    ReactiveUserDetailsService userDetailsServiceImpl;

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        final ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        if (!headers.isEmpty()) {
            final List<String> authorization = headers.get("Authorization");
            if (!CollectionUtils.isEmpty(authorization)) {
                String authHeader = authorization.get(0);
                if (StringUtils.hasText(authHeader)) {
                    if (authHeader.startsWith("Bearer ")) {
                        String bearer = authHeader.substring(7);
                        String username = jwtTokenManager.extractUsername(bearer);
                        if (StringUtils.hasText(username)) {
                            if (jwtTokenManager.isValid(bearer, username)) {
                                return Mono.just(
                                        new PreAuthenticatedAuthenticationToken(username, Const.USELESS));
                            }
                        }
                    }
                }
            }
        }

        return Mono.empty();
    }
}