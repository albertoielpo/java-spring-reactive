package net.ielpo.reactivestack.factory;

import net.ielpo.reactivestack.dto.BaseRes;
import reactor.core.publisher.Mono;

/**
 * @author Alberto Ielpo
 */
public class ResponseFactory {

    public static <T> Mono<BaseRes<T>> build(T payload) {
        return Mono.just(new BaseRes<T>(payload));
    }
}
