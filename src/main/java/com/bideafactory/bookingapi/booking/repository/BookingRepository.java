package com.bideafactory.bookingapi.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bideafactory.bookingapi.booking.domain.entity.Book;

public interface BookingRepository extends JpaRepository<Book, Long> {

    
}