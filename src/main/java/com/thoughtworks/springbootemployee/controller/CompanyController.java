package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping(path = "/companies")
@RestController
public class CompanyController {
    //TODO: you can practice constructor injection rather than field injection
    @Autowired
    private CompanyService companyService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping()
    public List<Company> listAll() {
        return companyService.listAll();
    }

    @GetMapping("/{id}")
    public Company findById(@PathVariable Long id) {
        return companyService.findById(id);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> findEmployeesByCompanyId(@PathVariable Long id) {
        return employeeService.findEmployeesByCompanyId(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/addCompany")
    public Company createCompany(@RequestBody Company company) {
        return companyService.createCompany(company);
    }

    @PutMapping("/updateCompany/{id}")
    public Company updateCompany(@PathVariable Long id, @RequestBody Company currentCompany) {
        Company company = companyService.findById(id);
        company.setName(currentCompany.getName());
        return company;
    }

    @DeleteMapping("/deleteCompany/{id}")
    public List<Company> deleteCompany(@PathVariable Long id) {
        return companyService.deleteCompany(id);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Company> listByPage(@RequestParam Long pageNumber, @RequestParam Long pageSize) {
        return companyService.listByPage(pageNumber, pageSize);
    }


}
