package com.bideafactory.bookingapi.booking.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BookingExceptions extends RuntimeException {
    private HttpStatus statusCode;
    private String error;
    private String message;

    public BookingExceptions(String error, String message, HttpStatus httpStatus) {
        super(message);
        this.error = error;
        this.message = message;
        this.statusCode = httpStatus;
    }
}
