package org.hzero.todo.api.controller.v1;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.hzero.todo.domain.entity.*;
import org.hzero.todo.domain.repository.CompanyRepository;
import org.hzero.todo.domain.repository.CustomerRepository;
import org.hzero.todo.domain.repository.OrderRepository;
import org.hzero.todo.app.service.ItemService;
import org.hzero.todo.app.service.SoHeaderService;
import org.hzero.todo.app.service.SoLineService;
import org.hzero.todo.infra.util.MyHeaderFooter;
import org.hzero.todo.infra.util.ToPdfUtils;
import org.hzero.todo.infra.util.Watermark;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;


/**
 * @Date 2020/9/3 16:19
 * @Author Summer_OneTree
 */
@RestController
@RequestMapping("/soLine")
public class SoLineController {
    private final SoLineService soLineService;
    private final SoHeaderService soHeaderService;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final ItemService itemService;

    public SoLineController(SoLineService soLineService,
                            SoHeaderService soHeaderService,
                            CompanyRepository companyRepository,
                            CustomerRepository customerRepository,
                            OrderRepository orderRepository, ItemService itemService) {
        this.soLineService = soLineService;
        this.soHeaderService = soHeaderService;
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.itemService = itemService;
    }

    /**
     * 更新行信息
     *
     * @return 成功条数
     */
    @RequestMapping("/update")
    public int update(@RequestParam(name = "itemCode", required = false) String itemCode,
                      @RequestParam(name = "orderQuantity", required = false) String orderQuantity,
                      @RequestParam(name = "unitSellingPrice", required = false) String unitSellingPrice,
                      @RequestParam(name = "description", required = false) String description,
                      @RequestParam(name = "orderNumber", required = false) String orderNumber,
                      @RequestParam(name = "lineNumber", required = false) String lineNumber,
                      @RequestParam(name = "addition1", required = false) String addition1,
                      @RequestParam(name = "addition2", required = false) String addition2,
                      @RequestParam(name = "addition3", required = false) String addition3,
                      @RequestParam(name = "addition4", required = false) String addition4,
                      @RequestParam(name = "addition5", required = false) String addition5) {

        System.out.println(description);

        SoHeader soHeader = new SoHeader();
        soHeader.setOrderNumber(orderNumber);

        SoLine soLine = new SoLine();

        // 当itemCode不为空时 设置值
        if (itemCode != null) {
            Item item = new Item();
            item.setItemCode(itemCode);
            Item selectOne = itemService.selectOne(item);
            if (selectOne != null) {
                Long itemId = selectOne.getItemId();
                soLine.setItemId(itemId);
            }
        }

        if (orderQuantity != null) {
            soLine.setOrderQuantity(BigDecimal.valueOf(Double.parseDouble(orderQuantity)));
        }
        if (unitSellingPrice != null) {
            soLine.setUnitSellingPrice(BigDecimal.valueOf(Double.parseDouble(unitSellingPrice)));
        }
        if (description != null) {
            soLine.setDescription(description);
        }
        soLine.setLineNumber(Integer.parseInt(lineNumber));

        if (addition1 != null) {
            soLine.setAddition1(addition1);
        }
        if (addition2 != null) {
            soLine.setAddition2(addition2);
        }
        if (addition3 != null) {
            soLine.setAddition3(addition3);
        }
        if (addition4 != null) {
            soLine.setAddition4(addition4);
        }
        if (addition5 != null) {
            soLine.setAddition5(addition5);
        }

        Order order = new Order();
        order.setSoHeader(soHeader);
        order.setSoLine(soLine);
        return soLineService.updateSoLine(order);
    }

///    /**
//     * 模拟高并发插入操作
//     *
//     * @param order Order
//     */
//    @RequestMapping("/insertTest")
//    public void insertTest(Order order) {
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                insert(order);
//            }
//        };
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        for (int i = 0; i < 100; i++) {
//            executorService.submit(runnable);
//        }
//    }

    /**
     * 插入订单行信息
     * Order中有订单编号和订单行信息
     * @return 返回成功条数
     */
    @RequestMapping("/insert")
    public int insert(@RequestParam(name = "itemCode", required = false) String itemCode,
                      @RequestParam(name = "orderQuantity", required = false) String orderQuantity,
                      @RequestParam(name = "unitSellingPrice", required = false) String unitSellingPrice,
                      @RequestParam(name = "description", required = false) String description,
                      @RequestParam(name = "orderNumber", required = false) String orderNumber,
                      @RequestParam(name = "addition1", required = false) String addition1,
                      @RequestParam(name = "addition2", required = false) String addition2,
                      @RequestParam(name = "addition3", required = false) String addition3,
                      @RequestParam(name = "addition4", required = false) String addition4,
                      @RequestParam(name = "addition5", required = false) String addition5) {

        Order order = new Order();

        SoHeader soHeader = new SoHeader();
        soHeader.setOrderNumber(orderNumber);
        order.setSoHeader(soHeader);

        Item item = new Item();
        item.setItemCode(itemCode);
        Item selectOne = itemService.selectOne(item);
        if (selectOne == null) {
            return 0;
        }
        Long itemId = selectOne.getItemId();;
        String itemUom = selectOne.getItemUom();;


        SoLine soLine = new SoLine();
        soLine.setItemId(itemId);
        soLine.setOrderQuantityUom(itemUom);
        if (orderQuantity == null) {
            return 0;
        }
        soLine.setOrderQuantity(BigDecimal.valueOf(Double.parseDouble(orderQuantity)));
        if (unitSellingPrice == null) {
            return 0;
        }
        soLine.setUnitSellingPrice(BigDecimal.valueOf(Double.parseDouble(unitSellingPrice)));
        soLine.setDescription(description);
        soLine.setAddition1(addition1);
        soLine.setAddition2(addition2);
        soLine.setAddition3(addition3);
        soLine.setAddition4(addition4);
        soLine.setAddition5(addition5);
        order.setSoLine(soLine);

        return soLineService.insertSoLine(order);
    }

    @RequestMapping("/out/{orderNumber}")
    public void outputPdf(@PathVariable(value = "orderNumber") String orderNumber, HttpServletResponse response) {

        // form表单数据
        Order order = new Order();

        SoHeader soHeaderByOrderNumber = soHeaderService.getSoHeaderByOrderNumber(orderNumber);

        Long companyId = soHeaderByOrderNumber.getCompanyId();
        Company company = new Company();
        company.setCompanyId(companyId);
        Company companyById = companyRepository.selectOne(company);

        Long customerId = soHeaderByOrderNumber.getCustomerId();
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        Customer customerById = customerRepository.selectOne(customer);

        order.setSoHeader(soHeaderByOrderNumber);
        order.setCompany(companyById);
        order.setCustomer(customerById);

        // 表格中行信息
        List<Order> detailList = orderRepository.getDetailList(orderNumber);

        try {
            // 1.新建document对象
            Document document = new Document(PageSize.A4);

            // 2.建立一个书写器(Writer)与document对象关联

            /// 存在本地
//            File file = new File("D:\\PDFDemo.pdf");
//            file.createNewFile();
//            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
//            writer.setPageEvent(new Watermark("HELLO ITEXTPDF"));
//            writer.setPageEvent(new MyHeaderFooter());

            /// 直接在浏览器中打开
//            response.setContentType("application/pdf");
//            response.setHeader("Expires", "0");
//            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
//            response.setHeader("Pragma", "public");
//            PdfWriter.getInstance(document, response.getOutputStream());

            // 在浏览器中下载
            String fileName = "order.pdf";
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.setContentType("application/octet-stream;charset=UTF-8");
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
            // 水印
            writer.setPageEvent(new Watermark("Order"));
            // 页眉/页脚
            writer.setPageEvent(new MyHeaderFooter());

            // 3.打开文档
            document.open();
            // 标题
            document.addTitle("Title@PDF-Order");
            // 作者
            document.addAuthor("Author@Summer_OneTree");
            // 主题
            document.addSubject("Subject@iText PDF Order");
            // 关键字
            document.addKeywords("Keywords@Order");
            // 创建者
            document.addCreator("Creator@Summer_OneTree`s");

            // 4.向文档中添加内容
            new ToPdfUtils().generatePDF(document, order, detailList);

            // 5.关闭文档
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
