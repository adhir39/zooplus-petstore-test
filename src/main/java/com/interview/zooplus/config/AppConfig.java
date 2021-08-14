package com.interview.zooplus.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties({
        PetStoreProp.class
})
public class AppConfig {
}
