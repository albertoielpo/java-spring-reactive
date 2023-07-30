package net.ielpo.reactivestack.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;

import net.ielpo.reactivestack.util.PortChecker;

/**
 * @author Alberto Ielpo
 */
@Configuration
public class ReactiveRedisConfiguration {

    @Value("${redis.connection.required}")
    private boolean redisConnectionRequired;

    @Value("${redis.connection.host}")
    private String redisConnectionHost;

    @Value("${redis.connection.port}")
    private Integer redisConnectionPort;

    public ReactiveRedisConfiguration() {

    }

    @Bean
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        return new LettuceConnectionFactory(this.redisConnectionHost, this.redisConnectionPort);
    }

    @Bean
    public ReactiveRedisOperations<String, Object> redisOperations(
            ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, Object> builder = RedisSerializationContext
                .newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Object> context = builder.value(serializer).hashValue(serializer)
                .hashKey(serializer).build();

        return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory, context);
    }

    @Bean
    public void redisChecker() {
        if (!redisConnectionRequired) {
            /** redis connection is not strictly required for the startup */
            return;
        }
        /* a mongodb connection is requried.. check if the DB is up and running */
        Assert.isTrue(PortChecker.isConnectionPossible(redisConnectionHost, redisConnectionPort),
                "Redis must be up and running");
    }
}