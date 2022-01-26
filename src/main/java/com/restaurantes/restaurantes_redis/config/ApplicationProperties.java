package com.restaurantes.restaurantes_redis.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class ApplicationProperties {
    private String host;
    private Integer port;
    private Boolean ssl;
    private Integer timeOfLife;
}
