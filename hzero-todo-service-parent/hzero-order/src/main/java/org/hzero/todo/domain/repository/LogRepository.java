package org.hzero.todo.domain.repository;

import org.apache.ibatis.plugin.Invocation;
import org.hzero.mybatis.base.BaseRepository;
import org.hzero.todo.domain.entity.Log;

/**
 * @Date 2020/8/26 9:26
 * @Author Summer_OneTree
 */
public interface LogRepository extends BaseRepository<Log> {

    /**
     * 插入日志信息异步操作
     * @param invocation Invocation
     * @param time 执行时间
     * @throws InterruptedException 异常
     */
    void insertLog(Invocation invocation, long time) throws InterruptedException;
}
