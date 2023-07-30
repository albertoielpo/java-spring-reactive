package net.ielpo.reactivestack.controller;

import java.time.Duration;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ielpo.reactivestack.config.redis.ReactiveRedisComponent;
import net.ielpo.reactivestack.dto.BaseRes;
import net.ielpo.reactivestack.dto.TestDto;
import net.ielpo.reactivestack.factory.ResponseFactory;
import net.ielpo.reactivestack.helper.JsonHelper;
import reactor.core.publisher.Mono;

/**
 * @author Alberto Ielpo
 *         TODO: remove this controller
 */
@RestController
@RequestMapping(path = "/cache")
public class CacheController {

    @Autowired
    private ReactiveRedisComponent reactiveRedisComponent;

    @Autowired
    private JsonHelper jsonHelper;

    private String cacheName = this.getClass().getName();

    @GetMapping(path = "/save/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TestDto> save(@PathVariable String id) {
        TestDto test = new TestDto(id, UUID.randomUUID().toString(), true);
        return reactiveRedisComponent.setTtl(cacheName, test.getA(), test, Duration.ofSeconds(30)).map(x -> test);
    }

    @GetMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BaseRes<TestDto>> get(@PathVariable String id) {

        Mono<TestDto> res = reactiveRedisComponent.get(cacheName, id).flatMap(x -> {
            return Mono.just(jsonHelper.convertAnyToObject(x, TestDto.class));
        });

        return ResponseFactory.build(res);

        // try {
        // Mono<TestDto> accessLogs = reactiveRedisComponent.get(cacheName,
        // id).flatMap(x -> {
        // return Mono.just(jsonHelper.convertAnyToObject(x, TestDto.class));
        // }).defaultIfEmpty(null); // throw NullPointerException
        // return ResponseFactory.build(accessLogs);
        // } catch (NullPointerException e) {
        // throw new BadRequestException("Data not found");
        // }
    }
}
