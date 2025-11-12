package com.flexisaf.FlexiSAF_wk9.service.impl;

import com.flexisaf.FlexiSAF_wk9.dto.LeaveRequestRequestDTO;
import com.flexisaf.FlexiSAF_wk9.entity.Employee;
import com.flexisaf.FlexiSAF_wk9.entity.LeaveRequest;
import com.flexisaf.FlexiSAF_wk9.exception.ResourceNotFoundException;
import com.flexisaf.FlexiSAF_wk9.repository.LeaveRequestRepository;
import com.flexisaf.FlexiSAF_wk9.repository.EmployeeRepository;
import com.flexisaf.FlexiSAF_wk9.service.LeaveRequestService;
import com.flexisaf.FlexiSAF_wk9.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository leaveRepo;
    private final EmployeeRepository employeeRepo;

    public LeaveRequestServiceImpl(LeaveRequestRepository leaveRepo, EmployeeRepository employeeRepo) {
        this.leaveRepo = leaveRepo;
        this.employeeRepo = employeeRepo;
    }

    @Override
    public LeaveRequest applyLeaveRequest(LeaveRequestRequestDTO dto) {
        Employee e = employeeRepo.findById(dto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + dto.getEmployeeId()));

        LeaveRequest l = MapperUtil.toLeaveEntity(dto);
        l.setEmployee(e);
        l.setStatus(LeaveRequest.LeaveStatus.PENDING);
        l.setDateApplied(LocalDate.now());
        return leaveRepo.save(l);
    }

    @Override
    public LeaveRequest getLeaveRequest(Long id) {
        return leaveRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Leave request not found with id " + id));
    }

    @Override
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRepo.findAll();
    }

    @Override
    public List<LeaveRequest> getLeaveRequestsByEmployee(Long employeeId) {
        // validate employee exist
        employeeRepo.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeeId));
        return leaveRepo.findByEmployeeId(employeeId);
    }

    @Override
    public LeaveRequest reviewLeaveRequest(Long id, LeaveRequestRequestDTO dto) {
        LeaveRequest l = getLeaveRequest(id);

        if (dto.getStatus() != null) {
            try {
                l.setStatus(LeaveRequest.LeaveStatus.valueOf(dto.getStatus()));
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Invalid status value");
            }
        }
        if (dto.getManagerComment() != null) l.setManagerComment(dto.getManagerComment());
        l.setDateReviewed(LocalDate.now());
        return leaveRepo.save(l);
    }

    @Override
    public void cancelLeave(Long id) {
        LeaveRequest l = getLeaveRequest(id);
        l.setStatus(LeaveRequest.LeaveStatus.CANCELLED);
        l.setDateReviewed(LocalDate.now());
        leaveRepo.save(l);
    }
}
