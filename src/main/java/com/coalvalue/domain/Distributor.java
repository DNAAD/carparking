package com.coalvalue.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by yuan zhao  on 10/18/2015.
 */
@Entity
@Table(name = "distributor")
public class Distributor extends BaseDomain {

    @Column(name = "company_no")
    private String companyNo;


    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;

    @Column(name = "logo")
    private String logo;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "fax_number")
    private String fax_number;

    @Column(name = "command")
    private String command;

    @Column(name = "abbreviation_name")
    private String abbreviationName;
    @Column(name = "advanced_payment_amount")
    private BigDecimal advancedPaymentAmount;

    @Column(name = "status")
    private String status;
    @Column(name = "synchronized_status")
    private String synchronizedStatus;


    @Column(name = "unique_Id")
    private String  uniqueId;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
    public String getSynchronizedStatus() {
        return synchronizedStatus;
    }

    public void setSynchronizedStatus(String synchronizedStatus) {
        this.synchronizedStatus = synchronizedStatus;
    }

    public BigDecimal getAdvancedPaymentAmount() {
        return advancedPaymentAmount;
    }

    public void setAdvancedPaymentAmount(BigDecimal advancedPaymentAmount) {
        this.advancedPaymentAmount = advancedPaymentAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Distributor() {
    }

    public String getCompanyNo() {
        return companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFax_number() {
        return fax_number;
    }

    public void setFax_number(String fax_number) {
        this.fax_number = fax_number;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getAbbreviationName() {
        return abbreviationName;
    }

    public void setAbbreviationName(String abbreviationName) {
        this.abbreviationName = abbreviationName;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
