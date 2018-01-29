package com.coalvalue.domain.entity;

import com.coalvalue.domain.BaseDomain;
import com.domain.entity.User;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;

/**
 * �û�����λ��model
 * 
 * @author liufeng
 * @date 2013-11-19
 */

@Entity
@Table(name = "no_generator")
public class NoGenerator extends BaseDomain {



    //@OneToOne(optional = true, cascade = CascadeType.ALL,fetch = FetchType.LAZY)// mappedBy="user")
    //@JoinColumn(name="company_Id" ,referencedColumnName = "id", unique = true)
   // private Company company;

    @Column(name = "company_Id")
    private Integer companyId;

    @OneToOne(optional = true, cascade = CascadeType.ALL,fetch = FetchType.LAZY)// mappedBy="user")
    @JoinColumn(name="user_Id" ,referencedColumnName = "id", unique = true)
    private User user;

	private Integer orderNo;
	private Integer shipmentNo;

    private Integer dealNo;
    private String typeName;
    private Integer value;


    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getShipmentNo() {
        return shipmentNo;
    }

    public void setShipmentNo(Integer shipmentNo) {
        this.shipmentNo = shipmentNo;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public void setDealNo(Integer dealNo) {
        this.dealNo = dealNo;
    }

    public Integer getDealNo() {
        return dealNo;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
