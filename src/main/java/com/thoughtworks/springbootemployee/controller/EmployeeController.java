package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(path = "/employees")
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
    private static final List<Employee> employees = new ArrayList<>();

    @GetMapping
    public List<Employee> listAll() {
        return employeeRepository.listAll();
    }

    @GetMapping("/{id}")
    public Employee findById(@PathVariable Long id) {
        return employeeRepository.findById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> findByGender(@RequestParam String gender) {
        return employeeRepository.findByGender(gender);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/addEmployee")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.saveEmployee(employee);
    }

    @PutMapping("/updateEmployee/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee currentEmployee) {
        Employee employee = employeeRepository.findById(id);
        employee.setName(currentEmployee.getName());
        employee.setAge(currentEmployee.getAge());
        employee.setGender(currentEmployee.getGender());
        employee.setSalary(currentEmployee.getSalary());
        return employee;
    }
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/deleteEmployee/{id}")
    public List<Employee> deleteEmployee(@PathVariable Long id) {
        return employeeRepository.deleteEmployee(id);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Employee> listByPage(@RequestParam Long pageNumber, @RequestParam Long pageSize) {
        return employeeRepository.listByPage(pageNumber, pageSize);
    }


}

