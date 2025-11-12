package com.flexisaf.FlexiSAF_wk9;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flexisaf.FlexiSAF_wk9.entity.Employee;
import com.flexisaf.FlexiSAF_wk9.repository.EmployeeRepository;
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
public class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        employeeRepository.deleteAll();
    }

    @Test
    @DisplayName("POST /api/v1/employees - should create employee")
    void shouldCreateEmployee() throws Exception {
        String employeeJson = """
            {
              "firstName": "Peace",
              "lastName": "Olufemi",
              "email": "peace@example.com",
              "department": "ICT",
              "position": "Engineer",
              "salary": 250000.0,
              "active": true,
              "address": "Lagos"
            }
        """;

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Employee created successfully"))
                .andExpect(jsonPath("$.data.firstName").value("Peace"));
    }

    @Test
    @DisplayName("GET /api/v1/employees - should return all employees")
    void shouldReturnAllEmployees() throws Exception {
        Employee emp = new Employee();
        emp.setFirstName("Peace");
        emp.setLastName("Olufemi");
        emp.setEmail("peace@example.com");
        emp.setPhoneNumber("08123456789");
        emp.setDepartment("ICT");
        emp.setPosition("Engineer");
        emp.setSalary(250000.0);
        emp.setDateofHire(LocalDate.now());
        emp.setStatus(Employee.EmploymentStatus.FULL_TIME);
        emp.setActive(true);
        emp.setAddress("lagos");
        employeeRepository.save(emp);

        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].firstName").value("Peace"));
    }

    @Test
    @DisplayName("GET /api/v1/employees/{id} - should return employee by ID")
    void shouldReturnEmployeeById() throws Exception {
        Employee emp = new Employee();
        emp.setFirstName("John");
        emp.setLastName("Doe");
        emp.setEmail("john@example.com");
        emp.setDepartment("HR");
        emp.setPosition("Manager");
        emp.setSalary(300000.0);
        employeeRepository.save(emp);

        mockMvc.perform(get("/api/v1/employees/" + emp.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Employee found"))
                .andExpect(jsonPath("$.data.email").value("john@example.com"));
    }

    @Test
    @DisplayName("PUT /api/v1/employees/{id} - should update employee")
    void shouldUpdateEmployee() throws Exception {
        Employee emp = new Employee();
        emp.setFirstName("Peace");
        emp.setLastName("Olufemi");
        emp.setEmail("peace@example.com");
        emp.setDepartment("ICT");
        emp.setPosition("Engineer");
        emp.setSalary(250000.0);
        employeeRepository.save(emp);

        String updatedJson = """
            {
              "firstName": "Peace",
              "lastName": "Johnson",
              "email": "peace@example.com",
              "department": "ICT",
              "position": "Lead Engineer",
              "salary": 300000.0
            }
        """;

        mockMvc.perform(put("/api/v1/employees/" + emp.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Employee updated successfully"))
                .andExpect(jsonPath("$.data.position").value("Lead Engineer"));
    }

    @Test
    @DisplayName("DELETE /api/v1/employees/{id} - should delete employee")
    void shouldDeleteEmployee() throws Exception {
        Employee emp = new Employee();
        emp.setFirstName("Delete");
        emp.setLastName("Me");
        emp.setEmail("deleteme@example.com");
        employeeRepository.save(emp);

        mockMvc.perform(delete("/api/v1/employees/" + emp.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Employee deleted successfully"));
    }
}
