package com.bideafactory.bookingapi.shared.external.api.discounts.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bideafactory.bookingapi.shared.external.api.discounts.domain.DiscountValidationRequest;
import com.bideafactory.bookingapi.shared.external.api.discounts.domain.DiscountValidationResponse;
import com.bideafactory.bookingapi.shared.external.api.discounts.service.DiscountService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import reactor.core.publisher.Mono;

@Service
public class DiscountServiceImpl implements DiscountService {

    private WebClient webClient;

    Logger logger = LoggerFactory.getLogger(DiscountService.class);

    public DiscountServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    @CircuitBreaker(name = "validateDiscount", fallbackMethod = "fallback")
    @Retry(name = "validateDiscount")
    // TODO: Implementar el TimeLimiter que necesita retornar un CompletionStage
    // @TimeLimiter(name = "validateDiscount") // Necesita CompletionStage
    public Mono<Boolean> validateDiscountCode(DiscountValidationRequest request) {
        logger.info("Making request to external discount API.");
        // Llamada el API externo.
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

    public Mono<Boolean> fallback(DiscountValidationRequest request, Throwable ex) {
        logger.warn("External discount API call fallback was called.");
        return Mono.just(false);
    }

}
