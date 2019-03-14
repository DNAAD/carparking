package com.coalvalue.domain.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Peter Xu on 06/30/2015.
 */
@Entity
@Table(name = "synchronized",catalog="storage")
public class SyncProperties {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



    @Column(name = "item_id")
    private Integer itemId;

    @Column(name = "item_type")
    private String itemType;


    @Column(name = "no")
    private String no;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "last_sync_timestamp")
    private LocalDateTime lastSyncTimestamp;

    @Column(name = "status")
    private String status;

/*    @Column(name = "type")
    private String type;*/
    private String syncStatus;
    private String syncSequence;
    private String distributorNo;
    private String localRemote;


    private Boolean isFromServer;
    private Boolean isModified;
    private Boolean isActive;
    @Transient
    private String content;
    private String objectUuid;
    @Transient
    private String extra;

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    private Integer syncTimes;

    public Boolean getFromServer() {
        return isFromServer;
    }

    public void setFromServer(Boolean fromServer) {
        isFromServer = fromServer;
    }

    public Boolean getModified() {
        return isModified;
    }

    public void setModified(Boolean modified) {
        isModified = modified;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getSyncSequence() {
        return syncSequence;
    }

    public void setSyncSequence(String syncSequence) {
        this.syncSequence = syncSequence;
    }

    public String getDistributorNo() {
        return distributorNo;
    }

    public void setDistributorNo(String distributorNo) {
        this.distributorNo = distributorNo;
    }


    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDateTime getLastSyncTimestamp() {
        return lastSyncTimestamp;
    }

    public void setLastSyncTimestamp(LocalDateTime lastSyncTimestamp) {
        this.lastSyncTimestamp = lastSyncTimestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

/*
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
*/

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public void setLocalRemote(String localRemote) {
        this.localRemote = localRemote;
    }

    public String getLocalRemote() {
        return localRemote;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getObjectUuid() {
        return objectUuid;
    }

    public void setObjectUuid(String objectUuid) {
        this.objectUuid = objectUuid;
    }

    public void setSyncTimes(Integer syncTimes) {
        this.syncTimes = syncTimes;
    }

    public Integer getSyncTimes() {
        return syncTimes;
    }
}
