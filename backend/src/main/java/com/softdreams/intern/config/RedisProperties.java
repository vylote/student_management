package com.softdreams.intern.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "redis")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RedisProperties {
    String host;
    int port;
}
