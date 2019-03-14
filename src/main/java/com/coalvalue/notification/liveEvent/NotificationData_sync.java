package com.coalvalue.notification.liveEvent;

import com.coalvalue.domain.entity.*;
import org.springframework.security.core.Authentication;

import java.util.List;

public class NotificationData_sync {
     
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
    private Distributor distributor;
    private InstanceTransport instanceTransport;
    private InventoryTransfer inventoryTransfer;
    private AdvancedPaymentTransfer advancedPaymentTransfer;
    private ReportDeliveryOrder deliveryOrder;
    private String storageNo;
    private String deliveryOrderNo;
    private List<String> deliveryOrderNos;

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

    public void setDistributor(Distributor distributor) {
        this.distributor = distributor;
    }

    public Distributor getDistributor() {
        return distributor;
    }

    public void setInstanceTransport(InstanceTransport instanceTransport) {
        this.instanceTransport = instanceTransport;
    }

    public InstanceTransport getInstanceTransport() {
        return instanceTransport;
    }

    public void setInventoryTransfer(InventoryTransfer inventoryTransfer) {
        this.inventoryTransfer = inventoryTransfer;
    }

    public InventoryTransfer getInventoryTransfer() {
        return inventoryTransfer;
    }

    public void setAdvancedPaymentTransfer(AdvancedPaymentTransfer advancedPaymentTransfer) {
        this.advancedPaymentTransfer = advancedPaymentTransfer;
    }

    public AdvancedPaymentTransfer getAdvancedPaymentTransfer() {
        return advancedPaymentTransfer;
    }

    public void setDeliveryOrder(ReportDeliveryOrder deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
    }

    public ReportDeliveryOrder getDeliveryOrder() {
        return deliveryOrder;
    }

    public void setStorageNo(String storageNo) {
        this.storageNo = storageNo;
    }

    public String getStorageNo() {
        return storageNo;
    }

    public void setDeliveryOrderNo(String deliveryOrderNo) {
        this.deliveryOrderNo = deliveryOrderNo;
    }

    public String getDeliveryOrderNo() {
        return deliveryOrderNo;
    }

    public void setDeliveryOrderNos(List<String> deliveryOrderNos) {
        this.deliveryOrderNos = deliveryOrderNos;
    }

    public List<String> getDeliveryOrderNos() {
        return deliveryOrderNos;
    }
    // getter and setter methods
}