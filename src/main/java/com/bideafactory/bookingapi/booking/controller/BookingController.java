package com.bideafactory.bookingapi.booking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bideafactory.bookingapi.booking.domain.dto.BookInDTO;
import com.bideafactory.bookingapi.booking.domain.service.SuccessResponse;
import com.bideafactory.bookingapi.booking.exception.BookingException;
import com.bideafactory.bookingapi.booking.service.BookingService;
import com.bideafactory.bookingapi.shared.external.api.discounts.domain.DiscountValidationRequest;
import com.bideafactory.bookingapi.shared.external.api.discounts.service.DiscountService;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
public class BookingController {
    private final BookingService bookingService;
    private final DiscountService discountService;

    public BookingController(BookingService bookingService, DiscountService discountService) {
        // Inyección de dependencias.
        this.bookingService = bookingService;
        this.discountService = discountService;
    }

    @PostMapping("/book")
    public Mono<ResponseEntity<SuccessResponse>> createBooking(@RequestBody @Valid BookInDTO request) {
        // A este punto el request debe estar validado, si está presente el discount code se verifica su validez.
        if (request.getDiscountCode() != null) {
            return discountService
                    .validateDiscountCode(new DiscountValidationRequest(request.getId(), request.getHouseId(),
                            request.getDiscountCode()))
                    .flatMap(isValid -> {
                        if (isValid) {
                            // Si el código es válido se genera la inserción.
                            bookingService.createBook(request);
                            return Mono.just(ResponseEntity.ok(new SuccessResponse(200, "Book Accepted")));
                        } else {
                            throw new BookingException("Conflict",
                                    "Invalid discount", HttpStatus.valueOf(400));
                        }
                    }).onErrorResume(e -> {
                        throw (BookingException) e;
                    });
        } else {
            // No hay código de descuento.
            bookingService.createBook(request);
            return Mono.just(ResponseEntity.ok(new SuccessResponse(200, "Book Accepted")));
        }
    }

}
