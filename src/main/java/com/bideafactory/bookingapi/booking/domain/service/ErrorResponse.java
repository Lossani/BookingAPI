package com.bideafactory.bookingapi.booking.domain.service;

import lombok.Data;

@Data
public class ErrorResponse {
    private int statusCode;
    private String error;
    private String message;

    public ErrorResponse(int statusCode, String error, String message) {
        this.statusCode = statusCode;
        this.error = error;
        this.message = message;
    }
}
