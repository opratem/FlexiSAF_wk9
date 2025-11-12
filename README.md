## Lesson 9: Integration Testing

This week’s task focused on **Integration Testing** in Spring Boot — ensuring that multiple layers of the application (Controller → Repository → Database) work together as expected.

### 🧪 Tools & Frameworks Used
- **JUnit 5** – for writing test cases
- **Spring Boot Test** – for test context management
- **MockMvc** – for simulating HTTP requests
- **H2 Database** – for in-memory database testing
- **AssertJ / JSONPath** – for assertions

### 🧩 Test Files
- `EmployeeControllerIntegrationTest.java`
    - Tests all `/api/v1/employees` endpoints
    - Verifies employee creation, retrieval, update, and deletion
- `LeaveRequestControllerIntegrationTest.java`
    - Tests all `/api/v1/leaves` endpoints
    - Verifies leave request creation, retrieval, update, and deletion
- `application-test.properties`
    - Configures an in-memory H2 database for integration testing

### 🧠 Key Learnings
- How to load the Spring Application Context for testing with `@SpringBootTest`
- Using `MockMvc` to simulate real HTTP calls
- Isolating tests with H2 DB (`application-test.properties`)
- Verifying JSON responses using `jsonPath`
- Ensuring all components integrate smoothly

### 🧩 Sample Test Run
