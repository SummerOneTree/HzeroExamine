package org.hzero.todo.app.service.impl;

import org.hzero.todo.domain.entity.Company;
import org.hzero.todo.domain.repository.CompanyRepository;
import org.hzero.todo.app.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Date 2020/9/2 19:18
 * @Author Summer_OneTree
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public List<Company> getAllCompany() {
        return companyRepository.selectAll();
    }

    @Override
    public Company selectOne(Company company) {
        return companyRepository.selectOne(company);
    }
}
