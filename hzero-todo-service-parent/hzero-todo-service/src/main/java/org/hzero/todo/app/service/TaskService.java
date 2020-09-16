package org.hzero.todo.app.service;

import org.hzero.todo.domain.entity.Task;

/**
 * 应用服务，事务控制、流程调度
 *
 * @Date 2020/8/14 10:47
 * @Author Summer_OneTree
 */
public interface TaskService {
    /**
     * 创建任务
     *
     * @param task 任务
     * @return Task
     */
    Task create(Task task);

    /**
     * 更新任务
     *
     * @param task 任务
     * @return Task
     */
    Task update(Task task);

    /**
     * 根据任务编号删除
     *
     * @param taskNumber 任务编号
     */
    void deleteByTaskNumber(String taskNumber);
}
