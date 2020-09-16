package org.hzero.todo.app.service.impl;

import org.hzero.todo.domain.entity.Customer;
import org.hzero.todo.domain.repository.CustomerRepository;
import org.hzero.todo.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Date 2020/9/4 14:21
 * @Author Summer_OneTree
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.selectAll();
    }

    @Override
    public Customer selectOne(Customer customer) {
        return customerRepository.selectOne(customer);
    }
}
