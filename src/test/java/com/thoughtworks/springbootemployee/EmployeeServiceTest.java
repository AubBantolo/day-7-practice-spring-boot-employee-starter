package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.exception.EmployeeIsInactiveException;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    private EmployeeService employeeService;

    private EmployeeRepository mockedEmployeeRepository;

    @BeforeEach
    void setUp() {
        mockedEmployeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeService(mockedEmployeeRepository);
    }

    @Test
    void should_return_created_employee_when_create_given_employee_service_and_employee_with_valid_age() {
        //Given
        Employee employee = new Employee(null, "Lucy", 20, "Female", 3000, 1L);
        Employee savedEmployee = new Employee(null, "Lucy", 20, "Female", 3000, 1L);

        when(mockedEmployeeRepository.saveEmployee(employee)).thenReturn(savedEmployee);
        //When
        Employee employeeResponse = employeeService.create(employee);

        //Then
        assertEquals(savedEmployee.getId(), employeeResponse.getId());
        assertEquals("Lucy", employeeResponse.getName());
        assertEquals(20, employeeResponse.getAge());
        assertEquals("Female", employeeResponse.getGender());
        assertEquals(3000, employeeResponse.getSalary());
        assertEquals(1L, employeeResponse.getCompanyId());
    }

    @Test
    void should_throw_exception_when_create_given_employee_service_and_employee_whose_age_is_less_than_18() {
        //Given
        Employee employee = new Employee(null, "Lucy", 16, "Female", 3000, 1L);
        //When
        EmployeeCreateException employeeCreateException = assertThrows(EmployeeCreateException.class, () -> {
            employeeService.create(employee);
        });
        assertEquals("Employee must be 18-65 years old.", employeeCreateException.getMessage());
        //Then
    }

    @Test
    void should_set_employee_active_status_to_true_default_when_create_new_employee_given_employee_active_status() {
        //Given
        Employee employee = new Employee(null, "Lucy", 19, "Female", 3000, 1L);
        Employee savedEmployee = new Employee(null, "Lucy", 20, "Female", 3000, 1L);

        when(mockedEmployeeRepository.saveEmployee(employee)).thenReturn(savedEmployee);
        //When
        Employee employeeResponse = employeeService.create(employee);
        //Then
        assertTrue(employeeResponse.isActive());
    }

    @Test
    void should_set_active_false_when_delete_given_employee_service_and_employee_id() {
        //Given
        Employee employee = new Employee(1L, "Lucy", 19, "Female", 3000, 1L);

        //When
        when(mockedEmployeeRepository.findById(employee.getId())).thenReturn(employee);
        employeeService.delete(employee.getId());

        //Then
        verify(mockedEmployeeRepository).updateEmployee(argThat(tempEmployee -> {
            assertEquals(tempEmployee.getId(), employee.getId());
            assertFalse(tempEmployee.isActive());
            return true;
        }));
    }

    @Test
    void should_verify_employee_active_or_not_when_updating_an_employee_given_employee_left_company() {
        //Given
        Employee employee = new Employee(1L, "Lucy", 19, "Female", 3000, 1L);
        employee.setActive(false);

        Employee updatedEmployee = new Employee(1L, "Lucy Test", 20, "Male", 3, 2L);

        //When
        when(mockedEmployeeRepository.findById(employee.getId())).thenReturn(employee);

        Exception exception = assertThrows(EmployeeIsInactiveException.class, () -> employeeService.update(updatedEmployee));

        //Then
        assertEquals("Employee is inactive", exception.getMessage());
    }

    @Test
    void should_verify_employee_active_or_not_when_updating_an_employee_given_employee_still_in_the_company() {
        //Given
        Employee employee = new Employee(1L, "Lucy", 19, "Female", 3000, 1L);
        employee.setActive(true);

        Employee updatedEmployee = new Employee(1L, "Lucy Test", 20, "Male", 3, 2L);

        //When
        when(mockedEmployeeRepository.findById(employee.getId())).thenReturn(employee);
        employeeService.update(updatedEmployee);

        //Then
        verify(mockedEmployeeRepository).updateEmployee(argThat(tempEmployee -> {
            assertTrue(tempEmployee.isActive());
            assertEquals(1L, tempEmployee.getId());
            assertEquals("Lucy Test", tempEmployee.getName());
            assertEquals(20, tempEmployee.getAge());
            assertEquals("Male", tempEmployee.getGender());
            assertEquals(3, tempEmployee.getSalary());
            assertEquals(2L, tempEmployee.getCompanyId());
            return true;
        }));
    }
}
