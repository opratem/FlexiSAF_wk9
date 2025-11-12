package com.flexisaf.FlexiSAF_wk9.service;

import com.flexisaf.FlexiSAF_wk9.dto.LeaveRequestRequestDTO;
import com.flexisaf.FlexiSAF_wk9.entity.LeaveRequest;

import java.util.List;

public interface LeaveRequestService {
    LeaveRequest applyLeaveRequest(LeaveRequestRequestDTO dto);
    LeaveRequest getLeaveRequest(Long id);
    List<LeaveRequest> getAllLeaveRequests();
    List<LeaveRequest> getLeaveRequestsByEmployee(Long employeeId);
    LeaveRequest reviewLeaveRequest(Long id, LeaveRequestRequestDTO dto);
    void cancelLeave(Long id);
}
