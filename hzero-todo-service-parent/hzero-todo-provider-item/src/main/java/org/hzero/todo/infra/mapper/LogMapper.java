package org.hzero.todo.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.hzero.todo.domain.entity.Log;

/**
 * @Date 2020/8/26 9:24
 * @Author Summer_OneTree
 */

public interface LogMapper extends BaseMapper<Log> {

    /**
     * 日志插入
     * @param log 日志实体
     */
    void insertLog(Log log);
}
