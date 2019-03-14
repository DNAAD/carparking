package com.coalvalue.notification;

import com.coalvalue.domain.entity.Company;
import com.coalvalue.domain.entity.Inventory;
import com.coalvalue.domain.entity.Product;
import com.coalvalue.domain.entity.User;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;

public class NotificationData_qrcode {
     
    private long id;
    private String name;
    private String email;
    private String mobile;
    private String fromUserName;
    private String toUserName;
    private String openId;
    private String eventKey;
    private String eventType;
    private String msgType;
    private String content;
    private Authentication authentication;
    private Object object;
    private Integer itemId;
    private Inventory inventory;
    private String eventName;
    private String message;
    private User user;
    private Company distributor;
    private String storageNo;

    private BigDecimal increment;
    private Product product;
    private String appSecret;
    private String appId;

    private Company company;
    private String type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setDistributor(Company distributor) {
        this.distributor = distributor;
    }

    public Company getDistributor() {
        return distributor;
    }

    public void setStorageNo(String storageNo) {
        this.storageNo = storageNo;
    }

    public String getStorageNo() {
        return storageNo;
    }


    public void setIncrement(BigDecimal increment) {
        this.increment = increment;
    }

    public BigDecimal getIncrement() {
        return increment;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }



    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    // getter and setter methods
}