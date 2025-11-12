package com.flexisaf.FlexiSAF_wk9.service.impl;

import com.flexisaf.FlexiSAF_wk9.dto.EmployeeRequestDTO;
import com.flexisaf.FlexiSAF_wk9.entity.Employee;
import com.flexisaf.FlexiSAF_wk9.exception.ResourceNotFoundException;
import com.flexisaf.FlexiSAF_wk9.repository.EmployeeRepository;
import com.flexisaf.FlexiSAF_wk9.service.EmployeeService;
import com.flexisaf.FlexiSAF_wk9.util.MapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repo;

    public EmployeeServiceImpl(EmployeeRepository repo) {
        this.repo = repo;
    }

    @Override
    public Employee createEmployee(EmployeeRequestDTO dto) {
        if (repo.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Employee with email already exists");
        }
        Employee e = MapperUtil.toEmployeeEntity(dto);
        return repo.save(e);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
    }

    @Override
    public Page<Employee> getEmployees(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public Employee updateEmployee(Long id, EmployeeRequestDTO dto) {
        Employee e = getEmployeeById(id);
        // Only update allowed fields; keep id and email immutable if desired
        if (dto.getFirstName() != null) e.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) e.setLastName(dto.getLastName());
        if (dto.getPhoneNumber() != null) e.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getDepartment() != null) e.setDepartment(dto.getDepartment());
        if (dto.getPosition() != null) e.setPosition(dto.getPosition());
        if (dto.getSalary() != null) e.setSalary(dto.getSalary());
        if (dto.getDateOfHire() != null) e.setDateofHire(dto.getDateOfHire());
        if (dto.getStatus() != null) {
            try { e.setStatus(Employee.EmploymentStatus.valueOf(dto.getStatus())); } catch (IllegalArgumentException ignored) {}
        }
        if (dto.getActive() != null) e.setActive(dto.getActive());
        if (dto.getAddress() != null) e.setAddress(dto.getAddress());
        return repo.save(e);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee e = getEmployeeById(id);
        repo.delete(e);
    }
}
