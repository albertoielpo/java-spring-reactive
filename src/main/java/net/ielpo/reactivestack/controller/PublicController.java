package net.ielpo.reactivestack.controller;

import org.springframework.http.MediaType;
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
@RequestMapping(path = "/public")
public class PublicController {

    @GetMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BaseRes<String>> getById(@PathVariable Integer id) {
        return ResponseFactory.build(String.format("This is a public controller - id param: %s", id));
    }

}
