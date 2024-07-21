package com.bideafactory.bookingapi.booking.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bideafactory.bookingapi.booking.domain.dto.BookInDTO;
import com.bideafactory.bookingapi.booking.domain.entity.Book;
import com.bideafactory.bookingapi.booking.service.BookingService;

@RestController
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public Book createBook(@RequestBody BookInDTO bookBody) {
        return this.bookingService.createBook(bookBody);
    }

}
