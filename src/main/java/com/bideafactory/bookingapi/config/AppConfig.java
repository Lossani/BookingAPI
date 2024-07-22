package com.bideafactory.bookingapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;
import io.github.resilience4j.common.retry.configuration.RetryConfigCustomizer;
import io.github.resilience4j.common.timelimiter.configuration.TimeLimiterConfigCustomizer;

import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;

@Configuration
public class AppConfig {
    @Value("${external.api.discountValidator.url}")
    private String externalApiUrl;

    @Value("${external.api.discountValidator.timeout}")
    private int timeout;

    @Value("${external.api.discountValidator.retries}")
    private int retries;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(externalApiUrl)
                .build();
    }

    @Bean
    public CircuitBreakerConfigCustomizer testCustomizer() {
        return CircuitBreakerConfigCustomizer
                .of("validateDiscount", builder -> builder
                        .failureRateThreshold(50)
                        .waitDurationInOpenState(Duration.ofMillis(1000)).slidingWindowSize(100));
    }

    @Bean
    public RetryConfigCustomizer retryRegistryCustomizer() {
        return RetryConfigCustomizer.of("validateDiscount", builder -> builder.maxAttempts(retries)
                .waitDuration(Duration.ofMillis(500)).build());
    }

    @Bean
    public TimeLimiterConfigCustomizer timeLimiterRegistryCustomizer() {
        return TimeLimiterConfigCustomizer.of("validateDiscount", builder -> builder
                .timeoutDuration(Duration.ofMillis(timeout))
                .build());
    }
}
