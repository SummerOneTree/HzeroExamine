package org.hzero.todo.infra.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.todo.domain.entity.*;
import org.hzero.todo.domain.repository.*;
import org.hzero.todo.infra.mapper.OrderMapper;
import org.hzero.todo.infra.util.PoiExcelUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2020/8/27 15:16
 * @Author Summer_OneTree
 */
@Slf4j
@Component
public class OrderRepositoryImpl extends BaseRepositoryImpl<Order> implements OrderRepository {

    public static final String SUFFIX = ".xlsx";
    public static final String NEW = "新建";
    public static final String SUBMITED = "已提交";
    public static final String APPROVED = "已审核";
    public static final String REJECTED = "已拒绝";
    private final OrderMapper orderMapper;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;
    private final SoLineRepository soLineRepository;
    private final SoHeaderRepository soHeaderRepository;

    public OrderRepositoryImpl(OrderMapper orderMapper,
                               CompanyRepository companyRepository,
                               CustomerRepository customerRepository,
                               ItemRepository itemRepository,
                               SoLineRepository soLineRepository, SoHeaderRepository soHeaderRepository) {
        this.orderMapper = orderMapper;
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
        this.soLineRepository = soLineRepository;
        this.soHeaderRepository = soHeaderRepository;
    }

    @Override
    public List<Order> getOrderList() {
        return orderMapper.getOrderList();
    }

    /**
     * 导出Excel
     * @param response HttpServletResponse
     * @param fileName 文件名
     */
    @Override
    public void exportData(HttpServletResponse response, String fileName) {
        log.info("导出数据开始...");
        fileName = fileName + SUFFIX;
        List<Order> items = orderMapper.getExportOrderList();
        List<String[]> list = new ArrayList<>();
        for (Order order : items) {
            String[] arrs = new String[items.size()];
            arrs[0] = String.valueOf(order.getSoHeader().getOrderNumber());
            arrs[1] = String.valueOf(order.getCompany().getCompanyNumber());

            arrs[2] = String.valueOf(order.getSoHeader().getOrderDate());
            arrs[2] = arrs[2].replaceAll("-","/");

            arrs[3] = String.valueOf(order.getSoHeader().getOrderStatus());
            if ("NEW".equals(arrs[3])) {
                arrs[3] = "新建";
            }
            if ("SUBMITED".equals(arrs[3])) {
                arrs[3] = "已提交";
            }
            if ("APPROVED".equals(arrs[3])) {
                arrs[3] = "已审核";
            }
            if ("REJECTED".equals(arrs[3])) {
                arrs[3] = "已拒绝";
            }

            arrs[4] = String.valueOf(order.getCustomer().getCustomerNumber());
            arrs[5] = String.valueOf(order.getSoLine().getLineNumber());
            arrs[6] = String.valueOf(order.getItem().getItemCode());
            arrs[7] = String.valueOf(order.getSoLine().getOrderQuantity());
            arrs[8] = String.valueOf(order.getSoLine().getOrderQuantityUom());
            arrs[9] = String.valueOf(order.getSoLine().getUnitSellingPrice());

            arrs[10] = String.valueOf(order.getSoLine().getDescription());
            arrs[11] = String.valueOf(order.getSoLine().getAddition1());
            arrs[12] = String.valueOf(order.getSoLine().getAddition2());
            arrs[13] = String.valueOf(order.getSoLine().getAddition3());
            arrs[14] = String.valueOf(order.getSoLine().getAddition4());
            arrs[15] = String.valueOf(order.getSoLine().getAddition5());

            list.add(arrs);
        }
        String[] head = {"order_number", "company_number", "order_date", "order_status",
                "customer_number", "line_number", "item_code", "order_quantity", "order_quantity_uom",
                "unit_selling_price", "description", "addition1", "addition2", "addition3",
                "addition4", "addition5"};

        ExcelData data = new ExcelData();
        data.setFileName(fileName);
        data.setHead(head);
        data.setData(list);
        data.setSheetName("hodr_so_header");

        try {
            PoiExcelUtil.exportExcel(response,data);
            log.info("导出数据结束。。。");
        } catch (Exception e) {
            log.info("数据导出失败。。。");
        }
    }

    /**
     * 导入Excel
     * @param fileName 文件名
     */
    @Override
    public void importData(String fileName) {
        log.info("开始导入数据。。。");
        try{
            List<Object[]> list = PoiExcelUtil.importExcel(fileName);
            if (list == null) {
                return;
            }
            int successInsertIntoSoHeader = 0;
            int successInsertIntoSoLine = 0;
            for (Object[] objects : list) {
                Order order = new Order();
                SoHeader soHeader = new SoHeader();
                Company company = new Company();
                Customer customer = new Customer();
                SoLine soLine = new SoLine();
                Item item = new Item();

                // soHeader赋值
                soHeader.setOrderNumber((String) objects[0]);
                // 查询数据库中是否有该订单编号的数据
                SoHeader soHeaderIsExist = soHeaderRepository.selectOne(soHeader);
                if (soHeaderIsExist != null) {
                    continue;
                }
                soHeader.setOrderDate(PoiExcelUtil.getDay(objects[2]));
                if (NEW.equals(objects[3])) {
                    soHeader.setOrderStatus("NEW");
                }
                if (SUBMITED.equals(objects[3])) {
                    soHeader.setOrderStatus("SUBMITED");
                }
                if (APPROVED.equals(objects[3])) {
                    soHeader.setOrderStatus("APPROVED");
                }
                if (REJECTED.equals(objects[3])) {
                    soHeader.setOrderStatus("REJECTED");
                }
                order.setSoHeader(soHeader);
                // company赋值
                company.setCompanyNumber((String) objects[1]);
                order.setCompany(company);
                // customer赋值
                customer.setCustomerNumber((String) objects[4]);
                order.setCustomer(customer);
                // soLine赋值
                double lineNumber = Double.parseDouble((String) objects[5]);
                int lineNumberNoZero = (int) lineNumber;
                soLine.setLineNumber(lineNumberNoZero);
                soLine.setOrderQuantity(new BigDecimal(objects[7].toString()));
                soLine.setOrderQuantityUom((String) objects[8]);
                soLine.setUnitSellingPrice(new BigDecimal(objects[9].toString()));
                order.setSoLine(soLine);
                // item赋值
                item.setItemCode((String) objects[6]);
                order.setItem(item);

                // 通过companyNumber得到companyId
                Long companyId = companyRepository.selectOne(company).getCompanyId();
                // 通过customerNumber得到customerId
                Long customerId = customerRepository.selectOne(customer).getCustomerId();
                soHeader.setCompanyId(companyId);
                soHeader.setCustomerId(customerId);
                // 插入头信息
                int successSoHeader = soHeaderRepository.insert(soHeader);
                if (successSoHeader == 1) {
                    successInsertIntoSoHeader++;
                }
                // 得到顶到头信息ID
                Long soHeaderId = soHeaderRepository.selectOne(soHeader).getSoHeaderId();
                Long itemId = itemRepository.selectOne(item).getItemId();
                soLine.setItemId(itemId);
                soLine.setSoHeaderId(soHeaderId);
                // 查询库中是否有该数据
                SoLine soLineSelect = new SoLine();
                soLineSelect.setSoHeaderId(soHeaderId);
                soLineSelect.setLineNumber(lineNumberNoZero);
                SoLine soLineIsExist = soLineRepository.selectOne(soLineSelect);
                if (soLineIsExist == null) {
                    // 插入需要导入的信息
                    int successSoLine = soLineRepository.insert(soLine);
                    if (successSoLine == 1) {
                        successInsertIntoSoLine++;
                    }
                }
            }
            log.info("成功插入hodr_so_header表:{}条，成功插入hodr_so_line表:{}条",successInsertIntoSoHeader,successInsertIntoSoLine);
            log.info("导入完成。。。");
        } catch (Exception e) {
            log.info("导入失败。。。");
            e.printStackTrace();
        }
    }

    /**
     * 得到 List<Order>
     * @param orderNumber 订单编号
     * @return List<Order>
     */
    @Override
    public List<Order> getDetailList(String orderNumber) {
        return orderMapper.getDetailList(orderNumber);
    }

    @Override
    public List<Order> getOrderBySelect(Order order) {
        String itemCode = order.getItem().getItemCode();
        Item itemByCode = itemRepository.getItemByCode(itemCode);
        if (itemByCode != null) {
            Long itemId = itemByCode.getItemId();
            order.getItem().setItemId(itemId);
        }
        System.out.println(order.toString());
        return orderMapper.getOrderBySelect(order);
    }
}
