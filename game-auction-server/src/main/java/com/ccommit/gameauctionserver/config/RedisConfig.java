package com.ccommit.gameauctionserver.config;

import com.ccommit.gameauctionserver.utils.CustomKeyGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * RedisConnectionFactory : 레디스에 Connection시의 객체를 생성하여 관리하는 인터페이스.
 * 레디스 데이터베이스와의 연결을 설정하는 역할. Spring Data Redis에서는 오픈소스인 Lettuce/Jedis가 주로 사용된다.
 * Spring Boot의 버전 업그레이드에 의해 Jedis는 지금 사용이 권장되지 않습니다.(deprecated)
 * LettuceConnectionFactory : 추상화 된 RedisConnection을 리턴해주는 비동기 라이브러리 Jedis에 비해 TPS가 높다.
 * RedisCacheManager : 스프링에서 추상화 인터페이스 CacheManager를 레디스 방식으로 구현한 클래스.
 * 캐싱시의 Key와 Value의 직렬화,역직렬화 할때 설정합니다.
 * 출처 : https://jojoldu.tistory.com/418
 */

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());

        GenericJackson2JsonRedisSerializer serializer =
                new GenericJackson2JsonRedisSerializer();

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);

        return redisTemplate;
    }

    @Bean
    public RedisCacheManager redisCacheManager() {

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                .disableCachingNullValues()
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(new GenericJackson2JsonRedisSerializer())
                )
                .entryTtl(Duration.ofDays(1L));

        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory())
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }

    @Bean
    public KeyGenerator customKeyGenerator(){
        return new CustomKeyGenerator();
    }
}
