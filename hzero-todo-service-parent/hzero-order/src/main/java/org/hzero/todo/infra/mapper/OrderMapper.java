package org.hzero.todo.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.hzero.todo.domain.entity.Order;
import org.hzero.todo.domain.entity.SoHeader;

import java.util.List;

/**
 * @Date 2020/8/27 14:40
 * @Author Summer_OneTree
 */
public interface OrderMapper extends BaseMapper<Order> {
    /**
     * 获取订单信息
     * @return 返回订单信息列表
     */
    List<Order> getOrderList();

    /**
     * 获取订单信息
     * @return 返回订单信息列表
     */
    List<Order> getExportOrderList();

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
