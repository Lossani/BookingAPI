package com.bideafactory.bookingapi.booking.domain.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table
public class Book {
    @Id
    private String id;

    private String name;
    private String lastname;
    private Integer age;
    private String phoneNumber;

    private LocalDate startDate;
    private LocalDate endDate;

    private String houseId;
    private String discountCode;
}
