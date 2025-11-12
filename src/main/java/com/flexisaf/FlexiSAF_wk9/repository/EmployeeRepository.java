package com.flexisaf.FlexiSAF_wk9.repository;

import com.flexisaf.FlexiSAF_wk9.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmail(String email);
}
