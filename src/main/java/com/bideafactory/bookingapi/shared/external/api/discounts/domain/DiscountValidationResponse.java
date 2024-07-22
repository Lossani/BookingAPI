package com.bideafactory.bookingapi.shared.external.api.discounts.domain;

import lombok.Data;

@Data
public class DiscountValidationResponse {
    private String houseld;
    private String discountCode;
    private int id;
    private String userld;
    private boolean status;
}
