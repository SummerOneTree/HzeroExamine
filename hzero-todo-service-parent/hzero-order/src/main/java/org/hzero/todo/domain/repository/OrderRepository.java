package org.hzero.todo.domain.repository;

import org.hzero.mybatis.base.BaseRepository;
import org.hzero.todo.domain.entity.Order;
import org.hzero.todo.domain.entity.SoHeader;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Date 2020/8/27 15:15
 * @Author Summer_OneTree
 */
public interface OrderRepository extends BaseRepository<Order> {
    /**
     * 获取订单信息
     * @return 返回订单信息列表
     */
    List<Order> getOrderList();
    /**
     * 导出数据为excel
     * @param response HttpServletResponse
     * @param fileName 文件名
     */
    void exportData(HttpServletResponse response, String fileName);

    /**
     * 导入excel数据
     * @param fileName 文件名
     */
    void importData(String fileName);

    /**
     * 获取明细界面数据
     * @param orderNumber 订单编号
     * @return List<Order>
     */
    List<Order> getDetailList(String orderNumber);

    /**
     * 条件查询
     * @param order Order
     * @return List<Order>
     */
    List<Order> getOrderBySelect(Order order);

}
