package net.ielpo.reactivestack.controller;

import java.time.Duration;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ielpo.reactivestack.config.redis.ReactiveRedisComponent;
import net.ielpo.reactivestack.dto.BaseRes;
import net.ielpo.reactivestack.dto.BikeDto;
import net.ielpo.reactivestack.factory.ResponseFactory;
import net.ielpo.reactivestack.helper.JsonHelper;
import reactor.core.publisher.Mono;

/**
 * @author Alberto Ielpo
 */
@RestController
@RequestMapping(path = "/bike")
public class BikeController {

    @Autowired
    private ReactiveRedisComponent reactiveRedisComponent;

    @Autowired
    private JsonHelper jsonHelper;

    private String cacheName = this.getClass().getName();

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BaseRes<Object>> save(@RequestBody BikeDto request) {
        request.setUuid(UUID.randomUUID().toString()); // set random UUID as unique ID
        String key = String.format("%s_%s", cacheName, request.getUuid());
        var res = reactiveRedisComponent.setTtl(key, request.getUuid(), request, Duration.ofSeconds(60));
        return ResponseFactory.build(res);
    }

    @GetMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BaseRes<BikeDto>> get(@PathVariable String id) {
        String key = String.format("%s_%s", cacheName, id);
        Mono<BikeDto> res = reactiveRedisComponent.get(key, id).flatMap(x -> {
            return Mono.just(jsonHelper.convertAnyToObject(x, BikeDto.class));
        });

        return ResponseFactory.build(res);

    }
}
