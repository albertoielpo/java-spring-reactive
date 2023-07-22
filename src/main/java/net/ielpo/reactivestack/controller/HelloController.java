package net.ielpo.reactivestack.controller;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ielpo.reactivestack.dto.BaseRes;
import net.ielpo.reactivestack.factory.ResponseFactory;
import reactor.core.publisher.Mono;

/**
 * @author Alberto Ielpo
 */
@RestController
@RequestMapping(path = "/hello")
public class HelloController {

    // Enum syntax
    // @PreAuthorize("hasRole(T(net.ielpo.reactivestack.config.AuthRole).USER.getName())
    // || hasRole(T(net.ielpo.reactivestack.config.AuthRole).ADMIN.getName())")

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BaseRes<String>> getById(@PathVariable Integer id) {
        return ResponseFactory.build(String.format("The id is %s", id));

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/admin/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BaseRes<String>> getByAdminId(@PathVariable Integer id) {
        return ResponseFactory.build(String.format("Admin route: The id is %s", id));
    }

}
