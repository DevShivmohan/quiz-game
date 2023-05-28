package com.samta.ai.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class CustomConfiguration {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    /**
     * API rate limiting 10 requests only for 10 hours
     * @return
     */
    @Bean
    public Bucket bucket(){
        return Bucket4j.builder().addLimit(Bandwidth.classic(10, Refill.intervally(10, Duration.ofHours(10)))).build();
    }
}
