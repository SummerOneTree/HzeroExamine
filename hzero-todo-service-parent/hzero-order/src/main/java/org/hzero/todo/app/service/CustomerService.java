package org.hzero.todo.app.service;

import org.hzero.todo.domain.entity.Customer;

import java.util.List;

/**
 * @Date 2020/9/4 14:20
 * @Author Summer_OneTree
 */
public interface CustomerService {
    /**
     * 给到所有的Customer
     * @return List<Customer>
     */
    List<Customer> getAllCustomer();

    /**
     * 得到一个 customer
     * @param customer Customer
     * @return Customer
     */
    Customer selectOne(Customer customer);
}
