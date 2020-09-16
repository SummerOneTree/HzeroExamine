package org.hzero.todo.app.service;

import org.hzero.todo.domain.entity.SoHeader;

/**
 * @Date 2020/9/3 15:47
 * @Author Summer_OneTree
 */
public interface SoHeaderService {

    /**
     * 插入一条订单头信息
     * @param soHeader SoHeader
     * @return 成功条数
     */
    int insertSoHeader(SoHeader soHeader);

    /**
     * 根据订单编号查询订单信息
     * @param orderNumber 订单编号
     * @return SoHeader
     */
    SoHeader getSoHeaderByOrderNumber(String orderNumber);

    /**
     * 根据主键更新SoHeader
     * @param soHeader SoHeader
     * @return 更新成功条数
     */
    int updateSoHeader(SoHeader soHeader);

}
