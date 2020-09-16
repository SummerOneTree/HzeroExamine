package org.hzero.todo.infra.repository.impl;

import org.hzero.mybatis.base.BaseRepository;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.todo.domain.entity.Customer;
import org.hzero.todo.domain.repository.CustomerRepository;
import org.springframework.stereotype.Component;

/**
 * @Date 2020/8/31 11:24
 * @Author Summer_OneTree
 */
@Component
public class CustomerRepositoryImpl extends BaseRepositoryImpl<Customer> implements CustomerRepository {
}
