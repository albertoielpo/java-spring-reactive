package net.ielpo.reactivestack.factory;

import net.ielpo.reactivestack.dto.BaseRes;
import reactor.core.publisher.Mono;

/**
 * @author Alberto Ielpo
 */
public class ResponseFactory {

    public static <T> Mono<BaseRes<T>> build() {
        return Mono.just(new BaseRes<T>());
    }

    /**
     * Return a Mono<BaseRes> from a T payload
     * 
     * @param <T>
     * @param payload
     * @return
     */
    public static <T> Mono<BaseRes<T>> build(T payload) {
        return Mono.just(new BaseRes<T>(payload));
    }

    /**
     * Return a Mono<BaseRes> from a Mono<T> payload
     * 
     * @param <T>
     * @param payload
     * @return
     */
    public static <T> Mono<BaseRes<T>> build(Mono<T> payload) {
        return Mono.just(new BaseRes<T>()).zipWith(payload).map(tuple -> {
            var baseRes = tuple.getT1();
            baseRes.data = tuple.getT2();
            return baseRes;
        });
    }

}
