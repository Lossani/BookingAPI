package com.bideafactory.bookingapi.booking.domain.dto;

import java.time.LocalDate;

import lombok.Data;

import jakarta.validation.constraints.*;

@Data
public class BookInDTO {
    @NotBlank
    @NotNull
    @Size(min = 9, max = 10, message = "ID must be between 9 and 10 characters long")
    private String id;

    @NotBlank
    @NotNull
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters long")
    private String name;

    @NotBlank
    @NotNull
    @Size(min = 2, max = 50, message = "Lastname must be between 2 and 50 characters long")
    private String lastname;

    @NotNull
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be no more than 100")
    private Integer age;

    @NotBlank
    @Size(min = 9, max = 20, message = "Phone number must be between 9 and 20 characters long")
    private String phoneNumber;

    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;

    @NotBlank
    @Size(min = 6, max = 15, message = "House ID must be between 6 and 15 characters long")
    private String houseId;

    @Size(min = 8, max = 8, message = "Discount code must be exactly 8 characters long")
    private String discountCode;
}
