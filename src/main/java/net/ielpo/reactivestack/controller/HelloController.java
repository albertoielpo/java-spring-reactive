package net.ielpo.reactivestack.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

/**
 * @author Alberto Ielpo
 */
@RestController
@RequestMapping(path = "/hello")
public class HelloController {

    // @PreAuthorize("hasRole(T(net.ielpo.reactivestack.config.AuthRole).USER.getName())")
    @GetMapping("/{id}")
    public Mono<String> getById(@PathVariable Integer id) {
        return Mono.just(String.format("The id is %s", id));
    }

}
