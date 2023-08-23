package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.exception.EmployeeIsInactiveException;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

    public List<Employee> listAll() {
        return employeeRepository.listAll();
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> findByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.saveEmployee(employee);
    }
}
