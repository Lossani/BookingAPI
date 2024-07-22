package com.bideafactory.bookingapi.booking.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.bideafactory.bookingapi.booking.domain.dto.BookInDTO;
import com.bideafactory.bookingapi.booking.domain.entity.Book;

@Component
public class BookInDTOToBook implements IMapper<BookInDTO, Book> {

    @Override
    public Book map(BookInDTO input) {
        Book book = new Book();
        BeanUtils.copyProperties(input, book);
        return book;
    }
}
