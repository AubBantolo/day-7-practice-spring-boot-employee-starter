package com.thoughtworks.springbootemployee.company;

import com.thoughtworks.springbootemployee.controller.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {

    private static final List<Company> companies = new ArrayList<>();

    static {
        companies.add(new Company(1L, "Spring"));
        companies.add(new Company(2L, "Autumn"));
        companies.add(new Company(3L, "Summer"));
        companies.add(new Company(4L, "Winter"));
    }

    public List<Company> listAll() {
        return companies;
    }

    public Company findById(Long id) {
        return companies.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .orElseThrow(CompanyNotFoundException::new);
    }

    public Company saveCompany(Company company) {
        company.setId(generateNextId());
        companies.add(company);
        return company;
    }

    private Long generateNextId() {
        return companies.stream()
                .mapToLong(Company::getId)
                .max()
                .orElse(0) + 1;
    }
}


