package com.thoughtworks.springbootemployee;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;
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

    @Test
    void should_return_the_employee_when_perform_given_employee_id() throws Exception {
        //Given
        Employee aubs = employeeRepository.saveEmployee(new Employee(1L, "Aubs", 23, "Female", 9000, 1L));
        employeeRepository.saveEmployee(new Employee(2L, "Juliet", 23, "Female", 9000, 2L));
        //When, Then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees/" + aubs.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(aubs.getId()))
                .andExpect(jsonPath("$.name").value(aubs.getName()))
                .andExpect(jsonPath("$.age").value(aubs.getAge()))
                .andExpect(jsonPath("$.gender").value(aubs.getGender()))
                .andExpect(jsonPath("$.salary").value(aubs.getSalary()))
                .andExpect(jsonPath("$.companyId").value(aubs.getCompanyId()));
    }

    @Test
    void should_return_404_when_not_found_given_employee_given_not_existing_id() throws Exception {
        //Given
        long notExistEmployeeId = 99L;
        //When
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees/" + notExistEmployeeId))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_list_of_employee_by_given_gender_when_perform_get_employee_given_gender() throws Exception{
        //Given
        Employee aubs = employeeRepository.saveEmployee(new Employee(1L, "Aubs", 23, "Female", 9000, 1L));
        Employee jul = employeeRepository.saveEmployee(new Employee(2L,"Jul", 23, "Male", 9000,1L));

        //when
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees/").param("gender","Female"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(aubs.getId()))
                .andExpect(jsonPath("$[0].name").value(aubs.getName()))
                .andExpect(jsonPath("$[0].age").value(aubs.getAge()))
                .andExpect(jsonPath("$[0].gender").value(aubs.getGender()))
                .andExpect(jsonPath("$[0].salary").value(aubs.getSalary()))
                .andExpect(jsonPath("$[0].companyId").value(aubs.getCompanyId()));
    }

    @Test
    void should_return_employee_created_when_perform_post_employees_given_new_employee_with_JSON_format() throws Exception {
        //Given
        ObjectMapper objectMapper = new ObjectMapper();
        Employee aubs = employeeRepository.saveEmployee(new Employee(1L, "Aubs", 23, "Female", 9000, 1L));

        mockMvcClient.perform(MockMvcRequestBuilders.post("/employees/addEmployee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aubs)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(notNullValue()))
                .andExpect(jsonPath("$.name").value("Aubs"))
                .andExpect(jsonPath("$.age").value(23))
                .andExpect(jsonPath("$.gender").value("Female"))
                .andExpect(jsonPath("$.salary").value(9000))
                .andExpect(jsonPath("$.companyId").value(1L));
    }

    @Test
    void should_return_the_employee_created_when_perform_post_employees_given_new_employee_with_JSON_format() throws Exception {
        //Given
        ObjectMapper objectMapper = new ObjectMapper();
        Employee aubs = new Employee(1L, "Aubs", 23, "Female", 9000, 1L);
        //When
        mockMvcClient.perform(MockMvcRequestBuilders.post("/employees/addEmployee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(aubs)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(notNullValue()))
                .andExpect(jsonPath("$.name").value("Aubs"))
                .andExpect(jsonPath("$.age").value(23))
                .andExpect(jsonPath("$.gender").value("Female"))
                .andExpect(jsonPath("$.salary").value(9000))
                .andExpect(jsonPath("$.companyId").value(1L));
        //Then
    }


}
