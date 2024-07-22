package com.bideafactory.bookingapi.booking.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

// Excepción personalizada
@Data
@EqualsAndHashCode(callSuper = false)
public class BookingException extends RuntimeException {
    private HttpStatus statusCode;
    private String error;
    private String message;

    public BookingException(String error, String message, HttpStatus httpStatus) {
        super(message);
        this.error = error;
        this.message = message;
        this.statusCode = httpStatus;
    }
}
