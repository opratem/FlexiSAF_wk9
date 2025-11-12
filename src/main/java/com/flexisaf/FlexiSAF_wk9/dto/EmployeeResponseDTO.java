package com.flexisaf.FlexiSAF_wk9.dto;

import lombok.Data;

@Data
public class EmployeeResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String department;
    private String position;
    private Double salary;
    private String status;
    private Boolean active;
    private String address;
}
