package com.bideafactory.bookingapi.booking.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.bideafactory.bookingapi.booking.domain.dto.BookInDTO;
import com.bideafactory.bookingapi.booking.domain.entity.Book;
import com.bideafactory.bookingapi.booking.domain.service.ErrorResponse;
import com.bideafactory.bookingapi.booking.domain.service.SuccessResponse;
import com.bideafactory.bookingapi.booking.service.BookingService;
import com.bideafactory.bookingapi.shared.external.api.discounts.domain.DiscountValidationRequest;
import com.bideafactory.bookingapi.shared.external.api.discounts.service.DiscountService;

import reactor.core.publisher.Mono;

@WebFluxTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private DiscountService discountService;

    @MockBean
    private BookingService bookingService;

    private BookInDTO validBookingRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        validBookingRequest = new BookInDTO();
        validBookingRequest.setId("14564088-4");
        validBookingRequest.setName("Gonzalo");
        validBookingRequest.setLastname("Pérez");
        validBookingRequest.setAge(33);
        validBookingRequest.setPhoneNumber("56988123222");
        validBookingRequest.setStartDate(LocalDate.parse("2024-07-22", DateTimeFormatter.ISO_LOCAL_DATE));
        validBookingRequest.setEndDate(LocalDate.parse("2024-07-22", DateTimeFormatter.ISO_LOCAL_DATE));
        validBookingRequest.setHouseId("213132");
        validBookingRequest.setDiscountCode("D0542A23");

        when(discountService.validateDiscountCode(any(DiscountValidationRequest.class)))
                .thenReturn(Mono.just(true));

        when(bookingService.createBook(
                any(BookInDTO.class))).thenReturn(new Book());

    }

    @Test
    public void testPostBooking_Success() {
        when(discountService.validateDiscountCode(any(DiscountValidationRequest.class)))
                .thenReturn(Mono.just(true));

        webTestClient.post()
                .uri("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(validBookingRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(SuccessResponse.class)
                .value(response -> {
                    assert response.getCode() == 200;
                    assert response.getMessage().equals("Book Accepted");
                });
    }

    @Test
    public void testPostBooking_InvalidDiscountCode() {
        when(discountService.validateDiscountCode(any(DiscountValidationRequest.class)))
                .thenReturn(Mono.just(false));

        webTestClient.post()
                .uri("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(validBookingRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorResponse.class)
                .value(response -> {
                    assert response.getStatusCode() == 400;
                    assert response.getError().equals("Conflict");
                    assert response.getMessage().equals("Invalid discount");
                });
    }

    @Test
    public void testPostBooking_MissingHouseId() {
        BookInDTO invalidRequest = new BookInDTO();
        invalidRequest.setId("14564088-4");
        invalidRequest.setName("Gonzalo");
        invalidRequest.setLastname("Pérez");
        invalidRequest.setAge(33);
        invalidRequest.setPhoneNumber("56988123222");
        invalidRequest.setStartDate(LocalDate.parse("2024-07-22", DateTimeFormatter.ISO_LOCAL_DATE));
        invalidRequest.setEndDate(LocalDate.parse("2024-07-22", DateTimeFormatter.ISO_LOCAL_DATE));
        invalidRequest.setDiscountCode("D0542A23");

        webTestClient.post()
                .uri("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isBadRequest();
    }
}
