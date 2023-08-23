package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.exception.EmployeeIsInactiveException;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
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

    public Employee update(Employee updatedEmployee) {
        Employee employee = employeeRepository.findById(updatedEmployee.getId());
        if (!employee.isActive()) {
            throw new EmployeeIsInactiveException();
        }
        employee.setName(updatedEmployee.getName());
        employee.setAge(updatedEmployee.getAge());
        employee.setGender(updatedEmployee.getGender());
        employee.setSalary(updatedEmployee.getSalary());
        return employeeRepository.updateEmployee(updatedEmployee);
    }

    public List<Employee> findEmployeesByCompanyId(@PathVariable Long id) {
        return employeeRepository.findByCompanyId(id);
    }



}
