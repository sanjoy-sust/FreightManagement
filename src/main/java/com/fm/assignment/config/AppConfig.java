package com.fm.assignment.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.omg.CORBA.UNKNOWN;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Sanjoy Kumer Deb
 * @since 07/10/2017.
 */
@EnableWebMvc
@Configuration
public class AppConfig {
    /**
     * This bean is used to allow Quited fields as field data.
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
}
