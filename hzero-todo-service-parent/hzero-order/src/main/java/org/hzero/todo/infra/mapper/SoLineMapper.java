package org.hzero.todo.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.hzero.todo.domain.entity.Order;
import org.hzero.todo.domain.entity.SoLine;
import org.springframework.data.repository.query.Param;

/**
 * @Date 2020/8/31 13:32
 * @Author Summer_OneTree
 */
public interface SoLineMapper extends BaseMapper<SoLine> {
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
