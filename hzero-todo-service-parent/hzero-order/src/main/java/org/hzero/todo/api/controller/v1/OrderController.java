package org.hzero.todo.api.controller.v1;

import org.hzero.todo.domain.entity.*;
import org.hzero.todo.domain.repository.OrderRepository;
import org.hzero.todo.app.service.OrderService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @Date 2020/8/27 15:19
 * @Author Summer_OneTree
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    public OrderController(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    /**
     * 得到所有的订单信息
     * @return List<Order>
     */
    @GetMapping("/list")
    public List<Order> list(){

        return orderRepository.getOrderList();
    }

    /**
     * 传入参数orderNumber 查询出订单明细信息
     * @param orderNumber 订单号
     * @return 然会次订单号的所有行信息
     */
    @RequestMapping({"/detailList/{orderNumber}", "/detailList"})
    public List<Order> detailList(@PathVariable(value = "orderNumber", required = false) String orderNumber) {
        return orderRepository.getDetailList(orderNumber);
    }

    /**
     * 导出订单信息
     * @param response HttpServletResponse
     * @param fileName 文件名
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response, String fileName){
        fileName = "OrderData";
        orderRepository.exportData(response, fileName);
    }

    /**
     * 导入订单信息
     * @param file 文件
     */
    @RequestMapping("/importExcel")
    public void importExcel(MultipartFile file) throws IOException {
        if (null == file) {
            String fileName = "C:\\Users\\Summer_OneTree\\Desktop\\hodr_so.xlsx";
            orderRepository.importData(fileName);
        } else {
            String filePath = "C:\\upload\\";
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            String suffixName = fileName.substring(fileName.lastIndexOf("."));

            // 随机生成
            fileName = UUID.randomUUID() + suffixName;
            fileName = filePath + fileName;

            file.transferTo(new File(fileName));

            orderRepository.importData(fileName);
        }
    }

    /**
     * 订单信息条件查询
     * @param order Order
     * @return List<Order>
     */
    @GetMapping("/listBySelect")
    public List<Order> getOrderBySelect(Order order) {
        Company company = new Company();
        company.setCompanyName("");

        Customer customer = new Customer();
        customer.setCustomerName("");

        SoHeader soHeader = new SoHeader();
        soHeader.setOrderNumber("SO190718");
        soHeader.setOrderStatus("");

        Item item = new Item();
        item.setItemCode("");

        order.setCompany(company);
        order.setCustomer(customer);
        order.setSoHeader(soHeader);
        order.setItem(item);

        return orderRepository.getOrderBySelect(order);
    }

    /**
     * 整单删除
     * @param orderNumber 订单编号
     */
    @RequestMapping({"/delete"})
    public int delete(@RequestParam(name = "orderNumber") String orderNumber) {
        int res = orderService.deleteOrder(orderNumber);
        System.out.println(res);
        return res;
    }

    /**
     * 条件查询
     * @return List<Order>
     */
    @RequestMapping("/select")
    public List<Order> selectTest(@RequestParam(name = "orderNumber", required = false) String orderNumber,
                           @RequestParam(name = "companyId", required = false) String companyId,
                           @RequestParam(name = "customerId", required = false) String customerId,
                           @RequestParam(name = "itemCode", required = false) String itemCode,
                           @RequestParam(name = "orderStatus", required = false) String orderStatus) {

        Order order = new Order();
        Company company = new Company();
        if (companyId != null && !"".equals(companyId)) {
            company.setCompanyId(Long.parseLong(companyId));
        }
        Customer customer = new Customer();
        if (customerId != null && !"".equals(customerId)) {
            customer.setCustomerId(Long.parseLong(customerId));
        }

        SoHeader soHeader = new SoHeader();
        soHeader.setOrderNumber(orderNumber);
        soHeader.setOrderStatus(orderStatus);

        Item item = new Item();
        item.setItemCode(itemCode);

        order.setCompany(company);
        order.setCustomer(customer);
        order.setSoHeader(soHeader);
        order.setItem(item);

        return orderRepository.getOrderBySelect(order);
    }
}
