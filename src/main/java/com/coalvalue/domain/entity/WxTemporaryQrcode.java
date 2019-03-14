package com.coalvalue.domain.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by silence on 2016/3/18.
 */
@Entity
@Table(name = "wx_temporary_qrcode",catalog="storage")

public class WxTemporaryQrcode extends BaseDomain {

    @Column(name = "scan_id")
    private Integer scanId;

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "app_id")
    private String appId;

    @Column(name = "scan_type")
    private String type;

    @Column(name = "item_type")
    private String itemType;

    @Column(name = "item_id")
    private Integer itemId;
    private String status;
    private Integer subScene;
    private String content;
    private String ticket;
    private Integer expireSeconds;
    private String channel;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getScanId() {
        return scanId;
    }

    public void setScanId(Integer scanId) {
        this.scanId = scanId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "qr_code")
    private String qrCode;

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }



    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setSubScene(Integer subScene) {
        this.subScene = subScene;
    }

    public Integer getSubScene() {
        return subScene;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getTicket() {
        return ticket;
    }
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public void setExpireSeconds(Integer expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public Integer getExpireSeconds() {
        return expireSeconds;
    }
}
