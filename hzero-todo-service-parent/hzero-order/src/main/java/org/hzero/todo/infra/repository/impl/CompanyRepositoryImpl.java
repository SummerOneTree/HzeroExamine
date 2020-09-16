package org.hzero.todo.infra.repository.impl;

import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.todo.domain.entity.Company;
import org.hzero.todo.domain.repository.CompanyRepository;
import org.springframework.stereotype.Component;

/**
 * @Date 2020/8/31 11:13
 * @Author Summer_OneTree
 */
@Component
public class CompanyRepositoryImpl extends BaseRepositoryImpl<Company> implements CompanyRepository {

}
