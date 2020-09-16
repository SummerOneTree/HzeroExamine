package org.hzero.todo.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Date 2020/8/27 14:04
 * @Author Summer_OneTree
 */
@ModifyAudit
@VersionAudit
@Table(name = "hodr_company")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Company {
    /**
     * 公司ID
     * 主键
     */
    @Id
    @GeneratedValue
    private Long companyId;
    /**
     * 公司编号
     */
    private String companyNumber;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 启用标识
     */
    private Boolean enabledFlag;

    /**
     * 乐观锁字段
     */
    private Long objectVersionNumber;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Boolean getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Boolean enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public Long getObjectVersionNumber() {
        return objectVersionNumber;
    }

    public void setObjectVersionNumber(Long objectVersionNumber) {
        this.objectVersionNumber = objectVersionNumber;
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyId=" + companyId +
                ", companyNumber='" + companyNumber + '\'' +
                ", companyName='" + companyName + '\'' +
                ", enabledFlag=" + enabledFlag +
                '}';
    }
}
