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
@RequestMapping(path = "/public")
public class PublicController {

    @GetMapping("/{id}")
    public Mono<String> getById(@PathVariable Integer id) {
        return Mono.just(String.format("This is a public controller - id param: %s", id));
    }

}