package com.coalvalue.enumType;

import com.coalvalue.configuration.CommonConstant;
import com.coalvalue.domain.pojo.ListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silence on 2016/1/18.
 */
public enum TransportOperationStatusEnum {



    CREATE (CommonConstant.TRANSPORT_OPERATION_STATUS_CREATE,"散单新建",1,""),
    CREATE_PENDING (CommonConstant.TRANSPORT_OPERATION_STATUS_CREATE_PENDING, "进场请求",2,""),
    AGREE_ENTER (CommonConstant.TRANSPORT_OPERATION_STATUS_AGREE_ENTER, "同意进场",2,""),

    LOADING (CommonConstant.TRANSPORT_OPERATION_STATUS_LOADING, "装载中",2,""),

    LEAVE (CommonConstant.TRANSPORT_OPERATION_STATUS_LEAVE, "已经离场",2,""),

    FORCE_LEAVE (CommonConstant.TRANSPORT_OPERATION_STATUS_LEAVE, "已经离场",2,""),

    LEAVE_AND_UPLOAD_CAMERA (CommonConstant.TRANSPORT_OPERATION_STATUS_LEAVE_AND_UPLOAD_CAMERA, "已经离场",2,""),

    COMPLETED (CommonConstant.TRANSPORT_OPERATION_STATUS_COMPLETED, "已经完成",2,""),
    CANCEL (CommonConstant.TRANSPORT_OPERATION_STATUS_CANCEL, "取消",2,""),



    CREATE_PENDING_CANVASSING (CommonConstant.TRANSPORT_OPERATION_STATUS_CREATE_PENDING_CANVASSING, "揽货到场新建进场请求",2,"") ,
    REJECT (CommonConstant.TRANSPORT_OPERATION_STATUS_REJECT, "拒绝",2,"") , LEAVE_CREATE_SHIPMENT(CommonConstant.TRANSPORT_OPERATION_STATUS_LEAVE_create_shipment, "离场 发运单",2,""),


    //COALPIT
    PARTNER_CREATE_PENDING (CommonConstant.TRANSPORT_OPERATION_STATUS_PARTNER_CREATE_PENDING, "合作商请求",2,"")  ,



    CREATE_PENDING_SCATTERED  (CommonConstant.TRANSPORT_OPERATION_CREATE_PENDING_SCATTERED, "散单请求装载",2,""),


    PENDING_ENTER_PARTNER (CommonConstant.TRANSPORT_OPERATION_STATUS_PENDING_ENTER_PARTNER, "合作 提煤请求",2,"")  ,
    PARTNER_LEAVE  (CommonConstant.TRANSPORT_OPERATION_PARTNER_LEAVE, "合作 离场",2,""),
    //PARTNER_LOADING  (CommonConstant.TRANSPORT_OPERATION_PARTNER_LOADING, "合作 装载中",2,""),
    Waiting_Print_delivery_order (CommonConstant.TRANSPORT_OPERATION_Waiting_Print_delivery_order, "等待打印提煤单",2,""),
    finance_audit_approved(CommonConstant.TRANSPORT_OPERATION_finance_audit_approved, "财务审核",2,""),
    finance_audit_pending (CommonConstant.TRANSPORT_OPERATION_finance_audit_pending, "财务审核中",2,""),
    Gathering_documents(CommonConstant.TRANSPORT_OPERATION_STATUS_Gathering_documents,"收集图片资料",2,"");

    ;


    //    FAVORITE_LEVEL_ELEVEN (CommonConstant.TRANSPORT_OPERATION_STATUS_LOADING, "收货关注",2,"");


    private final String statusText;
    private final String displayText;
    private final Integer id;

    private String helpMessage;

    public String getHelpMessage() {
        return helpMessage;
    }

    public void setHelpMessage(String helpMessage) {
        this.helpMessage = helpMessage;
    }

    public String getDisplayText() {
        return displayText;
    }

    private TransportOperationStatusEnum(String statusText, String displayText, Integer id, String helpMessage) {
        this.statusText = statusText;
        this.displayText = displayText;

        this.id = id;
        this.helpMessage = helpMessage;
    }
    public String getText() {
        return this.statusText;
    }

    public Integer getId() {
        return this.id;
    }


    public static List<ListItem> retriveTypese() {

        List<ListItem> list = new ArrayList<ListItem>();
        for (TransportOperationStatusEnum status : TransportOperationStatusEnum.values()) {
            ListItem element = new ListItem(status.getText(), status.getDisplayText(), status.getHelpMessage());
            list.add(element);
        }

        return list;

    }


    public static List<ListItem> retriveTypese(String statusText) {

        List<ListItem> list = new ArrayList<ListItem>();
        for(TransportOperationStatusEnum status : TransportOperationStatusEnum.values()) {
            ListItem element = new ListItem(status.getText(), status.getDisplayText());
            if (status.getText().equals(statusText)){
                element.setSelected(true);
            }
            list.add(element);
        }
        return list;

    }
    public static TransportOperationStatusEnum fromString(String text) {
        for (TransportOperationStatusEnum status : TransportOperationStatusEnum.values()) {
            if (status.getText().equals(text) ) {
                return status;
            }
        }
        System.out.println(" 找不到类型错误 text is :" + text);
        throw new RuntimeException("no customer status " + text);


    }

    public static List<ListItem> retriveTypeseNew(String scene) {
        List<ListItem> list = new ArrayList<ListItem>();



        if (ContextStateEnum.CONTEXT_SCENE_storage_loading_status_partner_status.name().equals(scene)) {
            ListItem element = new ListItem(PENDING_ENTER_PARTNER.getText(), PENDING_ENTER_PARTNER.getDisplayText(), PENDING_ENTER_PARTNER.getHelpMessage());
            list.add(element);
            element = new ListItem(PARTNER_LEAVE.getText(), PARTNER_LEAVE.getDisplayText(), PARTNER_LEAVE.getHelpMessage());
            list.add(element);
          //  element = new ListItem(PARTNER_LOADING.getText(), PARTNER_LOADING.getDisplayText(), PARTNER_LOADING.getHelpMessage());
            list.add(element);
            element = new ListItem(LOADING.getText(), LOADING.getDisplayText(), LOADING.getHelpMessage());



        }
        return list;
    }

    public static List<ListItem> retriveTypese(List<TransportOperationStatusEnum> transportOperationStatusEnums) {

        List<ListItem> list = new ArrayList<ListItem>();
        for(TransportOperationStatusEnum status : transportOperationStatusEnums) {
            ListItem element = new ListItem(status.getText(), status.getDisplayText());

            list.add(element);
        }
        return list;

    }

}
