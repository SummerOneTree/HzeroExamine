package org.hzero.todo.app.service;

/**
 * @Date 2020/8/31 11:06
 * @Author Summer_OneTree
 */
public interface OrderService {

    /**
     * 删除订单信息
     * @param orderNumber 订单编号
     * @return 删除条数
     */
    int deleteOrder(String orderNumber);
}
