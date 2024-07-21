package com.bideafactory.bookingapi.booking.service;

import com.bideafactory.bookingapi.booking.domain.dto.BookInDTO;
import com.bideafactory.bookingapi.booking.domain.entity.Book;

public interface BookingService {
    public Book createBook(BookInDTO bookInDTO);

    // public List<Book> findAll();

    // public Book updateBook(String id, BookInDTO updatedBook);

    // public void deleteById(String id);
}
