package com.bridee.api.configuration.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfiguration {

    @Value("${cache.manager.expireMinutes}")
    private Long expireTime;

    @Bean
    public CacheManager cacheManager(){
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager(CacheConstants.asArray());

        caffeineCacheManager.setCaffeine(Caffeine
                .newBuilder()
                .expireAfterWrite(Duration.ofMinutes(expireTime)));
        return caffeineCacheManager;
    }

}
