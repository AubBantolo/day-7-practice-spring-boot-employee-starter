package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;

public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee create(Employee employee) {

        if(employee.hasInvalidAge()){
            throw new EmployeeCreateException();
        }

        return employeeRepository.saveEmployee(employee);
    }

    public Employee delete(Long id) {
        Employee employee = employeeRepository.findById(id);
        employee.setActive(false);
        return employeeRepository.updateEmployee(employee);
    }
}
