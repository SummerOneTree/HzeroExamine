package org.hzero.todo.app.service;

import org.hzero.todo.domain.entity.Order;
import org.hzero.todo.domain.entity.SoLine;

/**
 * @Date 2020/9/3 16:20
 * @Author Summer_OneTree
 */
public interface SoLineService {
    /**
     * 更新订单行信息
     * @param order Order
     * @return 成功条数
     */
    int updateSoLine(Order order);

    /**
     * 通过lineNumber和soHeaderId 得到SoLine
     * @param soLine SoLine
     * @return SoLine
     */
    SoLine getSoLine(SoLine soLine);

    /**
     * 增加一条行信息
     * @param order Order
     * @return 成功条数
     */
    int insertSoLine(Order order);
}
