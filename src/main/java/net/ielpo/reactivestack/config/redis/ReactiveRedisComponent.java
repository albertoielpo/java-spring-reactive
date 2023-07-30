package net.ielpo.reactivestack.config.redis;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Alberto Ielpo
 */
@Component
public class ReactiveRedisComponent {

    @Autowired
    private ReactiveRedisOperations<String, Object> redisOperations;

    /**
     * Set key and value into a hash key
     * 
     * @param key     key value - must not be null.
     * @param hashKey hash key value - must not be null.
     * @param val     Object value
     * @return Mono of object
     */
    public Mono<Object> set(String key, String hashKey, Object val) {
        return redisOperations.opsForHash().put(key, hashKey, val).map(b -> val);
    }

    /**
     * 
     * @param key
     * @param hashKey
     * @param val
     * @param timeout
     * @return
     */
    public Mono<Object> setTtl(String key, String hashKey, Object val, Duration timeout) {
        var t1 = this.set(key, hashKey, val);
        var t2 = redisOperations.expire(key, timeout);
        return Mono.zip(t1, t2).map(tuple -> {
            return tuple.getT1();
        });
    }

    /**
     * Set ttl given a key
     * 
     * @param key
     * @param timeout
     * @return
     */
    public Mono<Boolean> setTtl(String key, Duration timeout) {
        return redisOperations.expire(key, timeout);
    }

    /**
     * @param key key value - must not be null.
     * @return Flux of Object
     */
    public Flux<Object> get(String key) {
        return redisOperations.opsForHash().values(key);
    }

    /**
     * Get value for given hashKey from hash at key.
     * 
     * @param key     key value - must not be null.
     * @param hashKey hash key value - must not be null.
     * @return Object
     */
    public Mono<Object> get(String key, Object hashKey) {
        return redisOperations.opsForHash().get(key, hashKey);
    }

    /**
     * Delete a key that contained in a hash key.
     * 
     * @param key     key value - must not be null.
     * @param hashKey hash key value - must not be null.
     * @return 1 Success or 0 Error
     */
    public Mono<Long> remove(String key, Object hashKey) {
        return redisOperations.opsForHash().remove(key, hashKey);
    }

}