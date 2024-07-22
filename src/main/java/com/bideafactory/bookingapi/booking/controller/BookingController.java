package com.bideafactory.bookingapi.booking.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bideafactory.bookingapi.booking.domain.dto.BookInDTO;
import com.bideafactory.bookingapi.booking.domain.entity.Book;
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
    Logger logger = LoggerFactory.getLogger(BookingController.class);

    public BookingController(BookingService bookingService, DiscountService discountService) {
        // Inyección de dependencias.
        this.bookingService = bookingService;
        this.discountService = discountService;
    }

    @PostMapping("/book")
    public Mono<ResponseEntity<SuccessResponse>> createBooking(@RequestBody @Valid BookInDTO request) {
        // A este punto el request debe estar validado, si está presente el discount code se verifica su validez.
        if (request.getDiscountCode() != null) {
            logger.debug("Discount code not null");
            return discountService
                    .validateDiscountCode(new DiscountValidationRequest(request.getId(), request.getHouseId(),
                            request.getDiscountCode()))
                    .flatMap(isValid -> {
                        if (isValid) {
                            // Si el código es válido se genera la inserción.
                            Book newBook = bookingService.createBook(request);
                            logger.info("Created booking: " + newBook);
                            return Mono.just(ResponseEntity.ok(new SuccessResponse(200, "Book Accepted")));
                        } else {
                            logger.warn("Invalid discount code attempt: " + request.toString());
                            throw new BookingException("Conflict",
                                    "Invalid discount", HttpStatus.valueOf(400));
                        }
                    }).onErrorResume(e -> {
                        logger.warn("External API request exception: " + e.toString());
                        throw (BookingException) e;
                    });
        } else {
            logger.debug("Discount code null");
            // No hay código de descuento.
            Book newBook = bookingService.createBook(request);
            logger.info("Created booking: " + newBook);
            return Mono.just(ResponseEntity.ok(new SuccessResponse(200, "Book Accepted")));
        }
    }

}
