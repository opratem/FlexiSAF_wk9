package com.flexisaf.FlexiSAF_wk9.util;

import com.flexisaf.FlexiSAF_wk9.dto.*;
import com.flexisaf.FlexiSAF_wk9.entity.Employee;
import com.flexisaf.FlexiSAF_wk9.entity.LeaveRequest;

public class MapperUtil {

    public static EmployeeResponseDTO toEmployeeResponse(Employee e) {
        if (e == null) return null;
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setId(e.getId());
        dto.setFirstName(e.getFirstName());
        dto.setLastName(e.getLastName());
        dto.setEmail(e.getEmail());
        dto.setPhoneNumber(e.getPhoneNumber());
        dto.setDepartment(e.getDepartment());
        dto.setPosition(e.getPosition());
        dto.setSalary(e.getSalary());
        dto.setStatus(e.getStatus() != null ? e.getStatus().name() : null);
        dto.setActive(e.isActive());
        dto.setAddress(e.getAddress());
        return dto;
    }

    public static Employee toEmployeeEntity(EmployeeRequestDTO dto) {
        if (dto == null) return null;
        Employee e = new Employee();
        e.setFirstName(dto.getFirstName());
        e.setLastName(dto.getLastName());
        e.setEmail(dto.getEmail());
        e.setPhoneNumber(dto.getPhoneNumber());
        e.setDepartment(dto.getDepartment());
        e.setPosition(dto.getPosition());
        e.setSalary(dto.getSalary() != null ? dto.getSalary() : 0.0);
        e.setDateofHire(dto.getDateOfHire());
        if (dto.getStatus() != null) {
            try {
                e.setStatus(Employee.EmploymentStatus.valueOf(dto.getStatus()));
            } catch (IllegalArgumentException ex) {
                // leave null; validation should catch invalid statuses if needed
            }
        }
        e.setActive(dto.getActive() != null ? dto.getActive() : true);
        e.setAddress(dto.getAddress());
        return e;
    }

    public static LeaveRequestResponseDTO toLeaveResponse(LeaveRequest l) {
        if (l == null) return null;
        LeaveRequestResponseDTO dto = new LeaveRequestResponseDTO();
        dto.setId(l.getId());
        if (l.getEmployee() != null) {
            dto.setEmployeeId(l.getEmployee().getId());
            dto.setEmployeeName(l.getEmployee().getFirstName() + " " + l.getEmployee().getLastName());
        }
        dto.setStartDate(l.getStartDate());
        dto.setEndDate(l.getEndDate());
        dto.setLeaveType(l.getLeaveType() != null ? l.getLeaveType().name() : null);
        dto.setStatus(l.getStatus() != null ? l.getStatus().name() : null);
        dto.setReason(l.getReason());
        dto.setManagerComment(l.getManagerComment());
        dto.setDateApplied(l.getDateApplied());
        dto.setDateReviewed(l.getDateReviewed());
        return dto;
    }

    public static LeaveRequest toLeaveEntity(LeaveRequestRequestDTO dto) {
        if (dto == null) return null;
        LeaveRequest l = new LeaveRequest();
        l.setStartDate(dto.getStartDate());
        l.setEndDate(dto.getEndDate());
        if (dto.getLeaveType() != null) {
            try {
                l.setLeaveType(LeaveRequest.LeaveType.valueOf(dto.getLeaveType()));
            } catch (IllegalArgumentException ignored) {}
        }
        l.setReason(dto.getReason());
        if (dto.getStatus() != null) {
            try {
                l.setStatus(LeaveRequest.LeaveStatus.valueOf(dto.getStatus()));
            } catch (IllegalArgumentException ignored) {}
        }
        l.setManagerComment(dto.getManagerComment());
        return l;
    }
}
