package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> listAll() {
        return companyRepository.listAll();
    }

    public Company findById(Long id) {
        return companyRepository.findById(id);
    }

    public Company createCompany(Company company) {
        return companyRepository.saveCompany(company);
    }

    public List<Company> deleteCompany(Long id) {
        return companyRepository.deleteCompany(id);
    }

    public List<Company> listByPage(Long pageNumber, Long pageSize) {
        return companyRepository.listByPage(pageNumber, pageSize);
    }
}
