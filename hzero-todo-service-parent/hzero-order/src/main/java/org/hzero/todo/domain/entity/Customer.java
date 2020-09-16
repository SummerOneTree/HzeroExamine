package org.hzero.todo.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Date 2020/8/27 14:05
 * @Author Summer_OneTree
 */
@ModifyAudit
@VersionAudit
@Table(name = "hodr_customer")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer {
    /**
     * 客户ID
     * 主键
     */
    @Id
    @GeneratedValue
    private Long customerId;
    /**
     * 客户编号
     */
    private String customerNumber;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 公司Id
     */
    private Long companyId;
    /**
     * 启用标识
     */
    private Boolean enabledFlag;

    /**
     * 乐观锁字段
     */
    private Long objectVersionNumber;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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
        return "Customer{" +
                "customerId=" + customerId +
                ", customerNumber='" + customerNumber + '\'' +
                ", customerName='" + customerName + '\'' +
                ", companyId=" + companyId +
                ", enabledFlag=" + enabledFlag +
                '}';
    }
}
