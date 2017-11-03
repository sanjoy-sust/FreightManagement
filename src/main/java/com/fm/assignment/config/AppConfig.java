package com.fm.assignment.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.omg.CORBA.UNKNOWN;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

/**
 * @author Sanjoy Kumer Deb
 * @since 07/10/2017.
 */
@EnableWebMvc
@Configuration
@EnableCaching
public class AppConfig {
    /**
     * This bean is customised to allow Quoted fields as field data.
     * Ex. : Cox's Bazar as place name. without this bean it will get exception.
     * @return
     */
    @Bean
    ObjectMapper getObjectMapper()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        return objectMapper;
    }
    @Bean
    CacheManager cacheManager() {

        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("findLocationWithin")));
        return cacheManager;
    }
}
