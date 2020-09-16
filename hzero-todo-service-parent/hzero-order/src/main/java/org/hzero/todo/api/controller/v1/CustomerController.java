package org.hzero.todo.api.controller.v1;

import org.hzero.todo.domain.entity.Customer;
import org.hzero.todo.app.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Date 2020/9/4 14:19
 * @Author Summer_OneTree
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * 查询所有的Customer信息
     * @return List<Customer>
     */
    @GetMapping("/list")
    public List<Customer> list() {
        return customerService.getAllCustomer();
    }
}
