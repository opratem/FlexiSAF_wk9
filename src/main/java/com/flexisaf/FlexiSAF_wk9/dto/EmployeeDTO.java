package com.flexisaf.FlexiSAF_wk9.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String department;
    private String position;
    private double salary;
    private LocalDate dateOfHire;
    private String status;
    private boolean active;
    private String address;
}
