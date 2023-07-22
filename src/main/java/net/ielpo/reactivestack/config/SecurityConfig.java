package net.ielpo.reactivestack.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

import net.ielpo.reactivestack.filter.JwtTokenAuthConverter;

/**
 * @author Alberto Ielpo
 */
@Configuration
@EnableReactiveMethodSecurity(useAuthorizationManager = true)
public class SecurityConfig {

    @Autowired
    private JwtTokenAuthConverter jwtTokenAuthConverter;

    @Autowired
    ReactiveAuthenticationManager authenticationManager;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.cors(cors -> cors.disable()).csrf(csfr -> csfr.disable()).authorizeExchange(exchanges -> {
            /** public routes... */
            exchanges.pathMatchers(
                    "/public/**", "/token/**").permitAll()

                    /** authenticated routes */
                    .anyExchange().authenticated();
        })
                /** custom filter - in this case jwt filter */
                .addFilterAt(jwtAuthenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }

    public AuthenticationWebFilter jwtAuthenticationWebFilter() {
        AuthenticationWebFilter filter = new AuthenticationWebFilter(authenticationManager);
        filter.setServerAuthenticationConverter(jwtTokenAuthConverter);
        return filter;
    }

    @Bean
    public WebSecurityCustomizer jwtWebSecurityCustomizer() throws Exception {
        return (web) -> web.ignoring().requestMatchers(HttpMethod.OPTIONS, "/**");
    }

}