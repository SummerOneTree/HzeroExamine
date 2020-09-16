package org.hzero.todo.infra.repository.impl;


import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.todo.domain.entity.Log;
import org.hzero.todo.domain.repository.LogRepository;
import org.hzero.todo.infra.mapper.LogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @Date 2020/8/26 9:28
 * @Author Summer_OneTree
 */
@Component
public class LogRepositoryImpl extends BaseRepositoryImpl<Log> implements LogRepository {

    private static final Logger logger = LoggerFactory.getLogger(LogRepositoryImpl.class);

    private final LogMapper logMapper;

    public LogRepositoryImpl(LogMapper logMapper) {
        this.logMapper = logMapper;
    }

    @Override
    @Async
    public void insertLog(Invocation invocation, long time) throws InterruptedException {

        Thread.sleep(5000);

        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        // 获取原始sql
        BoundSql boundSql =mappedStatement.getBoundSql(parameter);
        String oldSql = boundSql.getSql();

        // 获取参数
        Object parameterObject = boundSql.getParameterObject();
        String params = getParameterValue(parameterObject);

        // 获取ID
        String id = mappedStatement.getId();

        // 获取配置类
        Configuration configuration = mappedStatement.getConfiguration();

        //获取Sql类型
        String sqlCommandType = mappedStatement.getSqlCommandType().toString();

        String sqlCommandTypeSelect = "SELECT";

        if (!sqlCommandTypeSelect.equals(sqlCommandType) && !oldSql.contains("logger")) {
            logger.info("异步存储日志信息开始...");
            String sql = showSql(configuration, boundSql);

            Log log = new Log(sql, params, sqlCommandType, time);
            logMapper.insertLog(log);

            if (time >= 0) {
                // 日志输出
                logger.info("{}==>Parameters:{}", id, params);
                logger.info("{}==>sql:{}", id, sql);
                logger.info("{}==>sqlCommandType:{}", id, sqlCommandType);
                logger.info("{}==>time:{}ms", id, time);
            }
            logger.info("异步存储日志信息完成...");
        }

    }

    /**
     * 如果参数是String，则添加单引号
     * 如果是日期，则转换为时间格式器并加单引号
     * 对参数是null和不是null的情况作了处理
     *
     * @param obj sql参数
     * @return 返回参数
     */
    private String getParameterValue(Object obj) {
        StringBuilder params = new StringBuilder();
        if (obj instanceof String) {
            params.append("'").append(obj.toString()).append("'");
        } else if (obj instanceof Date) {
            DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            params.append("'").append(dateFormat.format(new Date())).append("'");
        } else {
            if (obj != null) {
                params.append(obj.toString());
            }
        }
        return params.toString();
    }

    /**
     * 参数替换问号
     *
     * @param configuration 传入配置类
     * @param boundSql      保存sql语句的对象
     * @return 返回有参数的sql语句
     */
    private String showSql(Configuration configuration, BoundSql boundSql) {
        // 获取参数
        Object parameterObject = boundSql.getParameterObject();
        // 将参数名存入ParameterMapping的property中
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");

        if (parameterMappings.size() > 0 && parameterObject != null) {
            // 从configuration配置对象中获得一个类型处理器注册器 typeHandlerRegistry
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            // 判断参数类型是否属于typeHandlerRegistry中的类型（类型中无自定义实体类）
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
            } else {
                // 如果parameterObject不为空，将对象parameterObject转换成metaObject,parameterObject不能判断是否有某个字段
                MetaObject metaObject = configuration.newMetaObject(parameterObject);

                // 前面有5个无需替换的参数需要跳过
                int index = 0;
                for (ParameterMapping parameterMapping : parameterMappings) {
                    index = index + 1;
                    if (index <= 5){
                        sql = sql.replaceFirst("\\?", "");
                        continue;
                    }
                    // 从parameterMapping中拿到字段名
                    String propertyName = parameterMapping.getProperty();
                    // 判断在metaObject对象中是否有这个字段名
                    if (metaObject.hasGetter(propertyName)) {
                        // 根据字段名取出字段值
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
        }
        return sql;
    }
}
