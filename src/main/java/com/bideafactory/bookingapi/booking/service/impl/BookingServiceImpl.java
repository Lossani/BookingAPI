package com.bideafactory.bookingapi.booking.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger logger = LoggerFactory.getLogger(BookingService.class);

    public BookingServiceImpl(BookingRepository repository, BookInDTOToBook mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Book createBook(BookInDTO bookInDTO) {
        logger.debug("createBook called");
        Book book = mapper.map(bookInDTO);
        Book newBook = this.repository.save(book);
        logger.debug("createBook finished inserting");
        return newBook;
    }

}
