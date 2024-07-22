package com.bideafactory.bookingapi.booking.domain.dto;

import java.time.LocalDate;

import lombok.Data;

import jakarta.validation.constraints.*;

@Data
public class BookInDTO {
    @NotBlank
    @NotNull
    private String id;

    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    private String lastname;
    @NotNull
    private Integer age;
    @NotBlank
    private String phoneNumber;

    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;

    @NotBlank
    private String houseId;
    private String discountCode;
}
