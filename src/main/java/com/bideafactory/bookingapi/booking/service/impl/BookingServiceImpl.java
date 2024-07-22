package com.bideafactory.bookingapi.booking.service.impl;

import org.springframework.stereotype.Service;

import com.bideafactory.bookingapi.booking.domain.dto.BookInDTO;
import com.bideafactory.bookingapi.booking.domain.entity.Book;
import com.bideafactory.bookingapi.booking.mapper.BookInDTOToBook;
import com.bideafactory.bookingapi.booking.repository.BookingRepository;
import com.bideafactory.bookingapi.booking.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository repository;
    private final BookInDTOToBook mapper;

    public BookingServiceImpl(BookingRepository repository, BookInDTOToBook mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Book createBook(BookInDTO bookInDTO) {
        Book book = mapper.map(bookInDTO);
        return this.repository.save(book);
    }

}
