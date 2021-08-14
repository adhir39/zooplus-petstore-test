package com.interview.zooplus.config;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.net.URI;

@Value
@ConstructorBinding
@ConfigurationProperties("pet-store")
public class PetStoreProp {

    URI url;
}
