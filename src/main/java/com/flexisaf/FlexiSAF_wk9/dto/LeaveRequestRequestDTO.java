package com.flexisaf.FlexiSAF_wk9.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveRequestRequestDTO {

    @NotNull(message = "Employee ID is reqquired")
    private Long employeeId;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotNull(message = "Leave type is required")
    private String leaveType; // CASUAL, SICK, MATERNITY, etc.

    private String reason;

    //Optional for mamager review
    private String status;
    private String managerComment;
}
