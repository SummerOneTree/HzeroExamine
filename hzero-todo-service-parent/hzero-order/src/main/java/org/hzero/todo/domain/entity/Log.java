package org.hzero.todo.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Date 2020/8/25 17:47
 * @Author Summer_OneTree
 */
@Table(name = "logger")
public class Log {
    @Id
    @GeneratedValue
    private Integer logId;
    private String logSql;
    private String logParams;
    private String logSqlCommandType;
    private Long logTime;

    public Log(String logSql, String logParams, String logSqlCommandType, Long logTime) {
        this.logSql = logSql;
        this.logParams = logParams;
        this.logSqlCommandType = logSqlCommandType;
        this.logTime = logTime;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getLogSql() {
        return logSql;
    }

    public void setLogSql(String logSql) {
        this.logSql = logSql;
    }

    public String getLogParams() {
        return logParams;
    }

    public void setLogParams(String logParams) {
        this.logParams = logParams;
    }

    public String getLogSqlCommandType() {
        return logSqlCommandType;
    }

    public void setLogSqlCommandType(String logSqlCommandType) {
        this.logSqlCommandType = logSqlCommandType;
    }

    public Long getLogTime() {
        return logTime;
    }

    public void setLogTime(Long logTime) {
        this.logTime = logTime;
    }

    @Override
    public String toString() {
        return "LogData{" +
                "logId=" + logId +
                ", logSql='" + logSql + '\'' +
                ", logParams='" + logParams + '\'' +
                ", logSqlCommandType='" + logSqlCommandType + '\'' +
                ", logTime=" + logTime +
                '}';
    }
}
