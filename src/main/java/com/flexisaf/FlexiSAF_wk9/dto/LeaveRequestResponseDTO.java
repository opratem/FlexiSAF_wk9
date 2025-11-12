package com.flexisaf.FlexiSAF_wk9.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveRequestResponseDTO {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private String leaveType;
    private String status;
    private String reason;
    private String managerComment;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate dateApplied;
    private LocalDate dateReviewed;

}
