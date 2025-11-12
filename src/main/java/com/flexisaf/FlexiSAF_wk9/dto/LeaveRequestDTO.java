package com.flexisaf.FlexiSAF_wk9.dto;

import com.flexisaf.FlexiSAF_wk9.entity.LeaveRequest;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveRequestDTO {
    private Long id;
    private Long employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveRequest.LeaveType leaveType;
    private String reason;
    private String managerComment;
    private LocalDate dateApplied;
    private LocalDate dateReviewed;

}
