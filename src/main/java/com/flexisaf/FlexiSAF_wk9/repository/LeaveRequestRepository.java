package com.flexisaf.FlexiSAF_wk9.repository;

import com.flexisaf.FlexiSAF_wk9.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByEmployeeId(Long emoloyeeId);
}
