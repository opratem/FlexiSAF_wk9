package com.flexisaf.FlexiSAF_wk9;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flexisaf.FlexiSAF_wk9.entity.Employee;
import com.flexisaf.FlexiSAF_wk9.entity.LeaveRequest;
import com.flexisaf.FlexiSAF_wk9.repository.EmployeeRepository;
import com.flexisaf.FlexiSAF_wk9.repository.LeaveRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class LeaveRequestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        leaveRequestRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    @DisplayName("POST /api/v1/leaves - should create leave request")
    void shouldCreateLeaveRequest() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("Peace");
        employee.setLastName("Olufemi");
        employee.setEmail("peace@example.com");
        employeeRepository.save(employee);

        String leaveJson = """
            {
              "employeeId": %d,
              "startDate": "2025-01-01",
              "endDate": "2025-01-05",
              "leaveType": "CASUAL",
              "reason": "Vacation"
            }
        """.formatted(employee.getId());

        mockMvc.perform(post("/api/v1/leaves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(leaveJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Leave request created successfully"))
                .andExpect(jsonPath("$.data.reason").value("Vacation"));
    }

    @Test
    @DisplayName("GET /api/v1/leaves - should return all leave requests")
    void shouldReturnAllLeaves() throws Exception {
        Employee emp = new Employee();
        emp.setFirstName("John");
        emp.setLastName("Doe");
        emp.setEmail("john@example.com");
        employeeRepository.save(emp);

        LeaveRequest leave = new LeaveRequest();
        leave.setEmployee(emp);
        leave.setStartDate(LocalDate.now());
        leave.setEndDate(LocalDate.now().plusDays(3));
        leave.setReason("Testing");
        leave.setLeaveType(LeaveRequest.LeaveType.CASUAL);
        leaveRequestRepository.save(leave);

        mockMvc.perform(get("/api/v1/leaves"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].reason").value("Testing"));
    }

    @Test
    @DisplayName("GET /api/v1/leaves/{id} - should return leave by ID")
    void shouldReturnLeaveById() throws Exception {
        Employee emp = new Employee();
        emp.setFirstName("Jane");
        emp.setLastName("Smith");
        emp.setEmail("jane@example.com");
        employeeRepository.save(emp);

        LeaveRequest leave = new LeaveRequest();
        leave.setEmployee(emp);
        leave.setStartDate(LocalDate.now());
        leave.setEndDate(LocalDate.now().plusDays(2));
        leave.setReason("Medical");
        leave.setLeaveType(LeaveRequest.LeaveType.SICK);
        leaveRequestRepository.save(leave);

        mockMvc.perform(get("/api/v1/leaves/" + leave.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Leave found"))
                .andExpect(jsonPath("$.data.reason").value("Medical"));
    }

    @Test
    @DisplayName("PUT /api/v1/leaves/{id} - should update leave request")
    void shouldUpdateLeaveRequest() throws Exception {
        Employee emp = new Employee();
        emp.setFirstName("Alex");
        emp.setLastName("Stone");
        emp.setEmail("alex@example.com");
        employeeRepository.save(emp);

        LeaveRequest leave = new LeaveRequest();
        leave.setEmployee(emp);
        leave.setStartDate(LocalDate.now());
        leave.setEndDate(LocalDate.now().plusDays(2));
        leave.setReason("Holiday");
        leave.setLeaveType(LeaveRequest.LeaveType.CASUAL);
        leaveRequestRepository.save(leave);

        String updateJson = """
            {
              "reason": "Family event",
              "managerComment": "Approved"
            }
        """;

        mockMvc.perform(put("/api/v1/leaves/" + leave.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Leave updated successfully"))
                .andExpect(jsonPath("$.data.managerComment").value("Approved"));
    }

    @Test
    @DisplayName("DELETE /api/v1/leaves/{id} - should delete leave request")
    void shouldDeleteLeaveRequest() throws Exception {
        Employee emp = new Employee();
        emp.setFirstName("Bob");
        emp.setLastName("Marley");
        emp.setEmail("bob@example.com");
        employeeRepository.save(emp);

        LeaveRequest leave = new LeaveRequest();
        leave.setEmployee(emp);
        leave.setStartDate(LocalDate.now());
        leave.setEndDate(LocalDate.now().plusDays(3));
        leave.setReason("Travel");
        leave.setLeaveType(LeaveRequest.LeaveType.CASUAL);
        leaveRequestRepository.save(leave);

        mockMvc.perform(delete("/api/v1/leaves/" + leave.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Leave deleted successfully"));
    }
}
