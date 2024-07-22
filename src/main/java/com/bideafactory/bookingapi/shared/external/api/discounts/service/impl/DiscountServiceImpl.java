package com.bideafactory.bookingapi.shared.external.api.discounts.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bideafactory.bookingapi.shared.external.api.discounts.domain.DiscountValidationRequest;
import com.bideafactory.bookingapi.shared.external.api.discounts.domain.DiscountValidationResponse;
import com.bideafactory.bookingapi.shared.external.api.discounts.service.DiscountService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import reactor.core.publisher.Mono;

@Service
public class DiscountServiceImpl implements DiscountService {

    private WebClient webClient;

    public DiscountServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    @CircuitBreaker(name = "validateDiscount", fallbackMethod = "fallback")
    @Retry(name = "validateDiscount")
    @TimeLimiter(name = "validateDiscount")
    public Mono<Boolean> validateDiscountCode(DiscountValidationRequest request) {
        return webClient.post()
                .uri("/")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(DiscountValidationResponse.class).flatMap(response -> {
                    if (response.isStatus()) {
                        return Mono.just(true);
                    } else {
                        return Mono.just(false);
                    }
                }).onErrorResume(e -> {
                    return Mono.just(false);
                });
    }

    public Mono<Boolean> fallback(String discountCode, Throwable ex) {
        return Mono.just(false);
    }

}
