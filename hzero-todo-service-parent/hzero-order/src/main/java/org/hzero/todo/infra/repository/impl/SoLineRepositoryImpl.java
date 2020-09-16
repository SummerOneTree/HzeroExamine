package org.hzero.todo.infra.repository.impl;

import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.todo.domain.entity.SoLine;
import org.hzero.todo.domain.repository.SoLineRepository;
import org.hzero.todo.infra.mapper.SoLineMapper;
import org.springframework.stereotype.Component;

/**
 * @Date 2020/8/31 13:31
 * @Author Summer_OneTree
 */
@Component
public class SoLineRepositoryImpl extends BaseRepositoryImpl<SoLine> implements SoLineRepository {

    private final SoLineMapper soLineMapper;

    public SoLineRepositoryImpl(SoLineMapper soLineMapper) {
        this.soLineMapper = soLineMapper;
    }

    /**
     * 得到最大订单号
     * @param lineNumber 订单编号
     * @return 最大订单号
     */
    @Override
    public Integer getMaxLineNumber(String lineNumber) {
        Integer maxLineNumber = soLineMapper.getMaxLineNumber(lineNumber);

        // 如果订单中没有行信息，则最大行号为空，需要判断排除异常
        if (maxLineNumber == null) {
            return 0;
        }
        return maxLineNumber;
    }

    @Override
    public int updateSoLine(SoLine soLine) {
        return soLineMapper.updateSoLine(soLine);
    }
}
