package com.bideafactory.bookingapi.booking.domain.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BookInDTO {
    private String id;

    private String name;
    private String lastname;
    private Integer age;
    private String phoneNumber;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String houseId;
    private String discountCode;
}
