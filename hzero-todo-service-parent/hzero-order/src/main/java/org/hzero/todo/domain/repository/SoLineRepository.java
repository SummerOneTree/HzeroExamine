package org.hzero.todo.domain.repository;

import org.hzero.mybatis.base.BaseRepository;
import org.hzero.todo.domain.entity.Order;
import org.hzero.todo.domain.entity.SoLine;

/**
 * @Date 2020/8/31 13:31
 * @Author Summer_OneTree
 */
public interface SoLineRepository extends BaseRepository<SoLine> {
    /**
     * 得到当前订单编号中最大的行号
     * @param lineNumber 订单编号
     * @return getMaxLineNumber
     */
    Integer getMaxLineNumber(String lineNumber);

    /**
     * 更新行信息
     * @param soLine soLine
     * @return 成功条数
     */
    int updateSoLine(SoLine soLine);
}
