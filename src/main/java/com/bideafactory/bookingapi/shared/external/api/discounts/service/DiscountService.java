package com.bideafactory.bookingapi.shared.external.api.discounts.service;

import com.bideafactory.bookingapi.shared.external.api.discounts.domain.DiscountValidationRequest;

import reactor.core.publisher.Mono;

public interface DiscountService {
    public Mono<Boolean> validateDiscountCode(DiscountValidationRequest request);

    public Mono<Boolean> fallback(DiscountValidationRequest request, Throwable ex);
}
