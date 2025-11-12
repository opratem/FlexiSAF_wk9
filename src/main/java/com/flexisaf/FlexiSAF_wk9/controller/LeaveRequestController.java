package com.flexisaf.FlexiSAF_wk9.controller;

import com.flexisaf.FlexiSAF_wk9.dto.ApiResponse;
import com.flexisaf.FlexiSAF_wk9.dto.LeaveRequestDTO;
import com.flexisaf.FlexiSAF_wk9.entity.Employee;
import com.flexisaf.FlexiSAF_wk9.entity.LeaveRequest;
import com.flexisaf.FlexiSAF_wk9.exception.ResourceNotFoundException;
import com.flexisaf.FlexiSAF_wk9.repository.EmployeeRepository;
import com.flexisaf.FlexiSAF_wk9.repository.LeaveRequestRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/leaves")
public class LeaveRequestController {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;

    public LeaveRequestController(LeaveRequestRepository leaveRequestRepository, EmployeeRepository employeeRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LeaveRequestDTO>>> getAllLeaveRequests() {
        List<LeaveRequestDTO> leaves = leaveRequestRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>("Leave requests retrieved successfully", leaves));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LeaveRequestDTO>> getLeaveById(@PathVariable Long id) {
        LeaveRequest leave = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));
        return ResponseEntity.ok(new ApiResponse<>("Leave found", convertToDto(leave)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<LeaveRequestDTO>> createLeaveRequest(@RequestBody LeaveRequestDTO dto) {
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        LeaveRequest leave = new LeaveRequest();
        BeanUtils.copyProperties(dto, leave);
        leave.setEmployee(employee);
        LeaveRequest saved = leaveRequestRepository.save(leave);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Leave request created successfully", convertToDto(saved)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LeaveRequestDTO>> updateLeaveStatus(@PathVariable Long id, @RequestBody LeaveRequestDTO dto) {
        LeaveRequest leave = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));

        // Only update non-null fields
        if (dto.getStartDate() != null) leave.setStartDate(dto.getStartDate());
        if (dto.getEndDate() != null) leave.setEndDate(dto.getEndDate());
        if (dto.getLeaveType() != null) leave.setLeaveType(dto.getLeaveType());
        if (dto.getReason() != null) leave.setReason(dto.getReason());
        if (dto.getManagerComment() != null) leave.setManagerComment(dto.getManagerComment());
        if (dto.getDateApplied() != null) leave.setDateApplied(dto.getDateApplied());
        if (dto.getDateReviewed() != null) leave.setDateReviewed(dto.getDateReviewed());

        LeaveRequest updated = leaveRequestRepository.save(leave);

        return ResponseEntity.ok(new ApiResponse<>("Leave updated successfully", convertToDto(updated)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteLeave(@PathVariable Long id) {
        LeaveRequest leave = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));
        leaveRequestRepository.delete(leave);
        return ResponseEntity.ok(new ApiResponse<>("Leave deleted successfully", null));
    }

    private LeaveRequestDTO convertToDto(LeaveRequest leave) {
        LeaveRequestDTO dto = new LeaveRequestDTO();
        BeanUtils.copyProperties(leave, dto);
        dto.setEmployeeId(leave.getEmployee().getId());
        return dto;
    }
}
