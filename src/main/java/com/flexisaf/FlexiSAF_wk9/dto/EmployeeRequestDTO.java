package com.flexisaf.FlexiSAF_wk9.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeRequestDTO {

    @NotBlank
    @Size(max = 50)
    private String firstName;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @Size(max = 20)
    private String phoneNumber;

    private String department;
    private String position;

    @PositiveOrZero
    private Double salary;

    private LocalDate dateOfHire;

    private String status; // FULL_TIME, PART_TIME, CONTRACT
    private Boolean active;
    private String address;
}
