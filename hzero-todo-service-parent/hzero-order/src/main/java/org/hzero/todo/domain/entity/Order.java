package org.hzero.todo.domain.entity;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * @Date 2020/8/31 13:40
 * @Author Summer_OneTree
 */
public class Order {
    /**
     * 公司实体
     */
    @Transient
    private Company company;
    /**
     * 顾客实体
     */
    @Transient
    private Customer customer;
    /**
     * 行信息实体
     */
    @Transient
    private SoLine soLine;
    /**
     * 物料实体
     */
    @Transient
    private Item item;
    /**
     * 订单头信息
     */
    @Transient
    private SoHeader soHeader;

    /**
     * 单个订单所有行金额的和
     */
    private BigDecimal totalPrice;


    /**
     * 用于条件查询的get和set
     * @return
     */
    public String getCompanyName() {
        if (company == null) {
            return null;
        }
        return company.getCompanyName();
    }
    public String getCustomerName() {
        if (customer == null) {
            return null;
        }
        return customer.getCustomerName();
    }
    public String getOrderNumber() {
        if (soHeader == null) {
            return null;
        }
        return soHeader.getOrderNumber();
    }
    public String getOrderStatus() {
        if (soHeader == null) {
            return null;
        }
        return soHeader.getOrderStatus();
    }
    public String getItemCode(){
        if (item == null) {
            return null;
        }
        return item.getItemCode();
    }
    public Integer getLineNumber() {
        if (soLine == null) {
            return null;
        }
        return soLine.getLineNumber();
    }
    public Date getOrderDate() {
        if (soHeader == null) {
            return null;
        }
        return soHeader.getOrderDate();
    }
    public Long getCompanyId() {
        if (company == null) {
            return null;
        }
        return company.getCompanyId();
    }
    public Long getCustomerId() {
        if (customer == null) {
            return null;
        }
        return customer.getCustomerId();
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public SoLine getSoLine() {
        return soLine;
    }

    public void setSoLine(SoLine soLine) {
        this.soLine = soLine;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public SoHeader getSoHeader() {
        return soHeader;
    }

    public void setSoHeader(SoHeader soHeader) {
        this.soHeader = soHeader;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "company=" + company +
                ", customer=" + customer +
                ", soLine=" + soLine +
                ", item=" + item +
                ", soHeader=" + soHeader +
                '}';
    }
}
