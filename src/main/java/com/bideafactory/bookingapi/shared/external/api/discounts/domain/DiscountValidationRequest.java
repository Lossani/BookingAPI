package com.bideafactory.bookingapi.shared.external.api.discounts.domain;

import lombok.Data;

@Data
public class DiscountValidationRequest {
    private String userId;
    private String houseId;
    private String discountCode;

    public DiscountValidationRequest(String userId, String houseId, String discountCode) {
        this.userId = userId;
        this.houseId = houseId;
        this.discountCode = discountCode;
    }
}
