package org.hzero.todo.config;


import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import org.hzero.todo.domain.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * @Date 2020/8/26 9:34
 * @Author Summer_OneTree
 */
@Component
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class})
})
public class SqlLogInterceptor implements Interceptor {

    private final LogRepository logRepository;

    @Autowired
    public SqlLogInterceptor(@Lazy LogRepository logRepository) {
        this.logRepository = logRepository;
    }


    @Override
    public Object intercept(Invocation invocation) throws InvocationTargetException, IllegalAccessException, InterruptedException {
        long start = System.currentTimeMillis();
        Object proceed = invocation.proceed();
        long end = System.currentTimeMillis();
        long time = end - start;
        logRepository.insertLog(invocation, time);
        return proceed;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
