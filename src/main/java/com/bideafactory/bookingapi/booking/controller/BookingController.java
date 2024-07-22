package com.bideafactory.bookingapi.booking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bideafactory.bookingapi.booking.domain.dto.BookInDTO;
import com.bideafactory.bookingapi.booking.domain.service.SuccessResponse;
import com.bideafactory.bookingapi.booking.exception.BookingExceptions;
import com.bideafactory.bookingapi.booking.service.BookingService;
import com.bideafactory.bookingapi.shared.external.api.discounts.domain.DiscountValidationRequest;
import com.bideafactory.bookingapi.shared.external.api.discounts.service.DiscountService;

import reactor.core.publisher.Mono;

@RestController
public class BookingController {
    private final BookingService bookingService;
    private final DiscountService discountService;

    public BookingController(BookingService bookingService, DiscountService discountService) {
        this.bookingService = bookingService;
        this.discountService = discountService;
    }

    @PostMapping
    public Mono<ResponseEntity<?>> createBooking(@RequestBody BookInDTO request) {
        if (request.getDiscountCode() != null) {
            return discountService
                    .validateDiscountCode(new DiscountValidationRequest(request.getId(), request.getHouseId(),
                            request.getDiscountCode()))
                    .flatMap(isValid -> {
                        if (isValid) {
                            bookingService.createBook(request);
                            return Mono.just(ResponseEntity.ok(new SuccessResponse(200, "Book Accepted")));
                        } else {
                            return Mono.just(ResponseEntity.badRequest().body(this.bookingService.createBook(request)));
                        }
                    }).onErrorResume(e -> {
                        BookingExceptions errorResponse = new BookingExceptions("Internal Server Error",
                                "An error occurred while processing the request", HttpStatus.valueOf(500));
                        return Mono.just(ResponseEntity.status(500).body(errorResponse));
                    });
        } else {
            // No hay código de descuento.
            bookingService.createBook(request);
            return Mono.just(ResponseEntity.ok(new SuccessResponse(200, "Book Accepted")));
        }
    }

}
