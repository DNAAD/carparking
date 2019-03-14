package com.coalvalue.enumType;

import com.coalvalue.domain.pojo.ListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silence on 2016/1/18.
 */
public enum DataSynchronizationTypeEnum {

    Transport ("Transport","Transport", 2,""),
    Inventory ("Inventory","Inventory", 2,""),
    Distributor ("Distributor","Distributor", 2,""),
    Qrcode ("Qrcode","Qrcode", 2,""),
    PriceCategory ("PriceCategory","PriceCategory", 2,""),

    Capital ("Capital","Capital", 2,""),

    Configuration ("Configuration","Configuration", 2,""),
    Configuration_request ("Configuration","Configuration", 2,""),

    Configuration_qrcode("Configuration_qrcode","Configuration_qrcode", 2,""),

    LiveInfo_storage ("LiveInfo_storage","LiveInfo_storage", 2,""),

    Employee ("Employee","Employee", 2,""),
    Account ("Account","Account", 2,""),
    Reconciliation ("Reconciliation","Reconciliation", 2,""),

    Rgister("Rgister","Rgister" ,2,"" ),
    Delivery_order ("Delivery_order","Delivery_order", 2,""),

    Image ("Image","Image", 2,""),
    Product ("Product","Product", 2,""),

    Follower ("Follower","Follower", 2,""),
    Inventory_single ("Inventory_single","Inventory_single", 2,""),




    QrcodeScan ("QrcodeScan","QrcodeScan", 2,""),

    TimeSlice ("TimeSlice","TimeSlice", 2,""),
    Group_quotation ("Group_quotation","Group_quotation", 2,""),
    Sync ("Sync","Sync", 2,""),
    Sync_compare ("Sync_compare","Sync_compare", 2,""),
    Sync_compare_than_sync ("Sync_compare_than_sync","Sync_compare_than_sync", 2,""),
    Sync_local ("Sync_local","Sync_local", 2,""),
    Sync_server_response ("Sync_server_response","Sync_server_response", 2,""),


    Probationary_quotation ("Probationary_quotation","Probationary_quotation", 2,""),
    Live ("Live","Live", 2,""),
    Currently_logged ("Currently_logged","Currently_logged", 2,""),

    Identity ("Identity","Identity", 2,""),
    Command ("Command","Command", 2,""),


    Live__queue ("Live__queue","Live__queue", 2,""),
    Live__principal ("Live__principal","Live__principal", 2,""),
    Differential_sync ("Differential_sync","Differential_sync", 2,""),


    ;




    ;





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

    private DataSynchronizationTypeEnum(String statusText, String displayText, Integer id, String helpMessage) {
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
        for (DataSynchronizationTypeEnum status : DataSynchronizationTypeEnum.values()) {
            ListItem element = new ListItem(status.getText(), status.getDisplayText(), status.getHelpMessage());
            list.add(element);
        }

        return list;

    }


    public static List<ListItem> retriveTypese(String statusText) {

        List<ListItem> list = new ArrayList<ListItem>();
        for(DataSynchronizationTypeEnum status : DataSynchronizationTypeEnum.values()) {
            ListItem element = new ListItem(status.getText(), status.getDisplayText());
            if (status.getText().equals(statusText)){
                element.setSelected(true);
            }
            list.add(element);
        }
        return list;

    }
    public static DataSynchronizationTypeEnum fromString(String text) {
        for (DataSynchronizationTypeEnum status : DataSynchronizationTypeEnum.values()) {
            if (status.getText().equals(text) ) {
                return status;
            }
        }
        throw new RuntimeException("no customer status " + text);
    }

    public static List<String> toStringList(List<DataSynchronizationTypeEnum> created) {

        List<String> status = new ArrayList<>();
        for(DataSynchronizationTypeEnum canvassingStatusEnum : created){
            status.add(canvassingStatusEnum.getText());
        }
        return status;
    }
}
