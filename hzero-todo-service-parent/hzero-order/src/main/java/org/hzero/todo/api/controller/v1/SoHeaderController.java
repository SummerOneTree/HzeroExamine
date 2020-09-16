package org.hzero.todo.api.controller.v1;

import org.hzero.todo.domain.entity.Company;
import org.hzero.todo.domain.entity.Customer;
import org.hzero.todo.domain.entity.SoHeader;
import org.hzero.todo.domain.repository.SoHeaderRepository;
import org.hzero.todo.app.service.CompanyService;
import org.hzero.todo.app.service.CustomerService;
import org.hzero.todo.app.service.SoHeaderService;
import org.hzero.todo.infra.util.SerialNumberUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

/**
 * @Date 2020/9/3 15:51
 * @Author Summer_OneTree
 */
@RestController
@RequestMapping("/soHeader")
public class SoHeaderController {
    private final SoHeaderService soHeaderService;
    private final CompanyService companyService;
    private final CustomerService customerService;
    private final SoHeaderRepository soHeaderRepository;

    public SoHeaderController(SoHeaderService soHeaderService,
                              CompanyService companyService,
                              CustomerService customerService,
                              SoHeaderRepository soHeaderRepository) {
        this.soHeaderService = soHeaderService;
        this.companyService = companyService;
        this.customerService = customerService;
        this.soHeaderRepository = soHeaderRepository;
    }

/// 暂时
//    /**
//     * 模拟高并发插入
//     * @param soHeader SoHeader
//     */
//    @RequestMapping("/insertTest")
//    public void insertTest(SoHeader soHeader) {
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                insertSoHeader(soHeader);
//            }
//        };
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        for (int i = 0; i < 100; i++) {
//            executorService.submit(runnable);
//        }
//    }

    /**
     * 订单头信息的插入操作
     *
     * @return 成功条数
     */
    @RequestMapping("/insert")
    public int insertSoHeader(@RequestParam(name = "orderNumber", required = false) String orderNumber,
                              @RequestParam(name = "companyName", required = false) String companyName,
                              @RequestParam(name = "customerName", required = false) String customerName,
                              @RequestParam(name = "orderStatus", required = false) String orderStatus,
                              @RequestParam(name = "orderDate", required = false) String orderDate) {
        System.out.println(orderNumber+companyName+customerName+orderDate+orderStatus);
        Company company = new Company();
        company.setCompanyName(companyName);
        Company selectOneCompany = companyService.selectOne(company);
        Long companyId = selectOneCompany.getCompanyId();

        Customer customer = new Customer();
        customer.setCustomerName(customerName);
        Customer selectOneCustomer = customerService.selectOne(customer);
        Long customerId = selectOneCustomer.getCustomerId();

        SoHeader soHeader = new SoHeader();
        soHeader.setCompanyId(companyId);
        soHeader.setCustomerId(customerId);
        soHeader.setOrderStatus(orderStatus);
        orderDate = orderDate.substring(0, orderDate.indexOf(" "));
        System.out.println(orderDate);
        soHeader.setOrderDate(Date.valueOf(orderDate));

        return soHeaderService.insertSoHeader(soHeader);
    }

    /**
     * 通过主键更新数据
     *
     * @return 成功条数
     */
    @RequestMapping("/update")
    public int updateSoHeader(@RequestParam(name = "orderNumber", required = false) String orderNumber,
                              @RequestParam(name = "companyName", required = false) String companyName,
                              @RequestParam(name = "customerName", required = false) String customerName,
                              @RequestParam(name = "orderStatus", required = false) String orderStatus,
                              @RequestParam(name = "orderDate", required = false) String orderDate) {

        System.out.println(orderNumber+companyName+customerName+orderDate+orderStatus);

        SoHeader soHeaderByOrderNumber = soHeaderService.getSoHeaderByOrderNumber(orderNumber);
        Long objectVersionNumber = soHeaderByOrderNumber.getObjectVersionNumber();
        Long soHeaderId = soHeaderByOrderNumber.getSoHeaderId();

        Company company = new Company();
        company.setCompanyName(companyName);
        Company selectOneCompany = companyService.selectOne(company);
        Long companyId = selectOneCompany.getCompanyId();

        Customer customer = new Customer();
        customer.setCustomerName(customerName);
        Customer selectOneCustomer = customerService.selectOne(customer);
        Long customerId = selectOneCustomer.getCustomerId();

        SoHeader soHeader = new SoHeader();
        soHeader.setSoHeaderId(soHeaderId);
        soHeader.setOrderNumber(orderNumber);
        soHeader.setCompanyId(companyId);
        orderDate = orderDate.substring(0, orderDate.indexOf(" "));
        soHeader.setOrderDate(Date.valueOf(orderDate));
        soHeader.setOrderStatus(orderStatus);
        soHeader.setCustomerId(customerId);
        soHeader.setObjectVersionNumber(objectVersionNumber);

        System.out.println(soHeader.toString());

        return soHeaderService.updateSoHeader(soHeader);
    }

    /**
     * 订单编号
     * 流水号测试
     * @return serialNumber
     */
    @RequestMapping("ser")
    public String ser(){
        String serialNumber = SerialNumberUtil.getSerialNumber();
        System.out.println(serialNumber);
        return serialNumber;
    }

    /**
     * 通过主键更新数据
     *
     * @return 成功条数
     */
    @RequestMapping("/submit")
    public int submitSoHeader(@RequestParam(name = "orderNumber", required = false) String orderNumber,
                              @RequestParam(name = "orderStatus", required = false) String orderStatus
                              ) {

        System.out.println(orderNumber+orderStatus);

        SoHeader soHeaderByOrderNumber = soHeaderService.getSoHeaderByOrderNumber(orderNumber);
        Long objectVersionNumber = soHeaderByOrderNumber.getObjectVersionNumber();
        Long soHeaderId = soHeaderByOrderNumber.getSoHeaderId();


        SoHeader soHeader = new SoHeader();
        soHeader.setSoHeaderId(soHeaderId);
        soHeader.setOrderNumber(orderNumber);
        soHeader.setOrderStatus(orderStatus);
        soHeader.setObjectVersionNumber(objectVersionNumber);

        System.out.println(soHeader.toString());

        return soHeaderRepository.updateSoHeader(soHeader);
    }
}
