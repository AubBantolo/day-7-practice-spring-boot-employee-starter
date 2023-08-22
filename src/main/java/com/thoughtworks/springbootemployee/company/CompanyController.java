package com.thoughtworks.springbootemployee.company;

import com.thoughtworks.springbootemployee.controller.Employee;
import com.thoughtworks.springbootemployee.controller.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping(path = "/companies")
@RestController
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping()
    public List<Company> listAll(){
        return companyRepository.listAll();
    }

    @GetMapping("/{id}")
    public Company findById(@PathVariable Long id){
        return companyRepository.findById(id);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> findEmployeesByCompanyId(@PathVariable Long id){
        return employeeRepository.findByCompanyId(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/addCompany")
    public Company createCompany (@RequestBody Company company){
        return companyRepository.saveCompany(company);
    }
    @DeleteMapping("/deleteCompany/{id}")
    public List<Company> deleteCompany(@PathVariable Long id){
        return companyRepository.deleteCompany(id);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Company> listByPage(@RequestParam Long pageNumber, @RequestParam Long pageSize){
        return companyRepository.listByPage(pageNumber, pageSize);
    }




}
