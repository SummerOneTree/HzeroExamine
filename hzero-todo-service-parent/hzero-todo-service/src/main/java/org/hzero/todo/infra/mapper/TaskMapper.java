package org.hzero.todo.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.hzero.todo.domain.entity.Task;

import java.util.List;

/**
 * @Date 2020/8/14 10:50
 * @Author Summer_OneTree
 */
public interface TaskMapper extends BaseMapper<Task> {
    /**
     * 查询任务
     *
     * @param params 任务查询参数
     * @return Task
     */
    List<Task> selectTask(Task params);
}
