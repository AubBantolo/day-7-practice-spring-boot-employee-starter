package com.thoughtworks.springbootemployee;


import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeApiTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MockMvc mockMvcClient;

    @BeforeEach
    void cleanupEmployeeData(){
        employeeRepository.cleanAll();
    }

    @Test
    void should_return_all_give_employees_when_perform_given_employees() throws Exception {
        //Given
        Employee aubs = employeeRepository.saveEmployee(new Employee(1L, "Aubs", 23, "Female", 9000, 1L));
        //When, Then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(aubs.getId()))
                .andExpect(jsonPath("$[0].name").value(aubs.getName()))
                .andExpect(jsonPath("$[0].age").value(aubs.getAge()))
                .andExpect(jsonPath("$[0].gender").value(aubs.getGender()))
                .andExpect(jsonPath("$[0].salary").value(aubs.getSalary()))
                .andExpect(jsonPath("$[0].companyId").value(aubs.getCompanyId()));
    }
}
