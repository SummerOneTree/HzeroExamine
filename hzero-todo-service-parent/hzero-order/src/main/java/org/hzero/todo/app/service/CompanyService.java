package org.hzero.todo.app.service;

import org.hzero.todo.domain.entity.Company;

import java.util.List;

/**
 * @Date 2020/9/2 19:17
 * @Author Summer_OneTree
 */
public interface CompanyService {

    /**
     * 得到所有的Company
     * @return List<Company>
     */
    List<Company> getAllCompany();

    /**
     * 得到一个company
     * @param company Company
     * @return Company
     */
    Company selectOne(Company company);
}
