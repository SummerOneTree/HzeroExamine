package org.hzero.todo.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Date 2020/8/18 15:45
 * @Author Summer_OneTree
 */
@ApiModel("物料信息")
@ModifyAudit
@VersionAudit
@Table(name = "todo_item")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Item extends AuditDomain {

    /**
     * 物料Id，主键，自增
     */
    @Id
    @GeneratedValue
    private Long itemId;

    /**
     * 物料编码
     */
    @ApiModelProperty("物料编码")
    private String itemCode;

    /**
     * 物料单位
     */
    @ApiModelProperty("物料单位")
    private String itemUom;

    /**
     * 物料描述
     */
    @ApiModelProperty("物料描述")
    private String itemDescription;

    /**
     * 物料生效时间
     */
    @ApiModelProperty("物料生效时间")
    private Date startActiveDate;

    /**
     * 物料失效时间
     */
    @ApiModelProperty("物料失效时间")
    private Date endActiveDate;

    /**
     * 物料是否启用
     */
    @ApiModelProperty("物料是否启用")
    private Boolean enabledFlag;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemUom() {
        return itemUom;
    }

    public void setItemUom(String itemUom) {
        this.itemUom = itemUom;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Date getStartActiveDate() {
        return startActiveDate;
    }

    public void setStartActiveDate(Date startActiveDate) {
        this.startActiveDate = startActiveDate;
    }

    public Date getEndActiveDate() {
        return endActiveDate;
    }

    public void setEndActiveDate(Date endActiveDate) {
        this.endActiveDate = endActiveDate;
    }

    public Boolean getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Boolean enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", itemCode='" + itemCode + '\'' +
                ", itemUom='" + itemUom + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", startActiveDate=" + startActiveDate +
                ", endActiveDate=" + endActiveDate +
                ", enabledFlag=" + enabledFlag +
                '}';
    }
}
