package com.thoughtworks.springbootemployee.controller;

import org.springframework.expression.spel.ast.Literal;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private static final List<Employee> employees = new ArrayList<>();

    static{
        employees.add(new Employee(1L,"Gerard",25,"male",99999));
        employees.add(new Employee(2L,"Aubrey",23,"female",99239));
        employees.add(new Employee(3L,"Jo",24,"male",99239));
        employees.add(new Employee(4L,"Ju",24,"female",99239));
        employees.add(new Employee(5L,"Christian",24,"male",99239));
        employees.add(new Employee(6L,"Manalac",24,"male",99239));
        employees.add(new Employee(7L,"Joseph",24,"male",99239));
    }

    public List<Employee> listAll() {
        return employees;
    }

    public Employee findById(Long id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public Employee saveEmployee(Employee employee) {
        Long id = generateNextId();

        Employee employeeToBeCreated = new Employee(id, employee.getName(), employee.getAge(), employee.getGender(), employee.getSalary());
        employees.add(employeeToBeCreated);

        return employeeToBeCreated;
    }

    public List<Employee> deleteEmployee(Long id){
        employees.remove(findById(id));
        return employees;
    }

    private Long generateNextId() {
        return employees.stream()
                .mapToLong(Employee::getId)
                .max()
                .orElse(0) + 1;
    }

    public List<Employee> listByPage(Long pageNumber, Long pageSize) {
        return employees.stream()
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

}