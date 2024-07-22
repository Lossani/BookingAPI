package com.bideafactory.bookingapi.booking.domain.service;

import lombok.Data;

@Data
public class SuccessResponse {
    private int code;
    private String message;

    public SuccessResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
