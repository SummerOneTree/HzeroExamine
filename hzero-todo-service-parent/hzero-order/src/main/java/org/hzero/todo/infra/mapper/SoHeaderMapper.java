package org.hzero.todo.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.hzero.todo.domain.entity.SoHeader;

import java.util.List;

/**
 * @Date 2020/8/31 13:46
 * @Author Summer_OneTree
 */
public interface SoHeaderMapper extends BaseMapper<SoHeader> {
    /**
     * 提交更新
     * @param soHeader soHeader
     * @return 成功与否
     */
    int updateSoHeader(SoHeader soHeader);
}
