package com.coalvalue.enumType;

import com.coalvalue.domain.pojo.ListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silence on 2016/1/18.
 */
public enum EchoSessionTypeEnum {


    Whole ("Whole","Whole", 2,""),
    Sync_portion ("Sync_portion","Sync_portion", 2,""),

    Identity_bootup("Identity_bootup","Identity_bootup", 2,""),
    Identity_temp("Identity_temp","Identity_temp", 2,""),
    Register_bootup("Register_bootup","Register_bootup", 2,""),
    Register_temp("Register_temp","Register_temp", 2,""),


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

    private EchoSessionTypeEnum(String statusText, String displayText, Integer id, String helpMessage) {
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
        for (EchoSessionTypeEnum status : EchoSessionTypeEnum.values()) {
            ListItem element = new ListItem(status.getText(), status.getDisplayText(), status.getHelpMessage());
            list.add(element);
        }

        return list;

    }


    public static List<ListItem> retriveTypese(String statusText) {

        List<ListItem> list = new ArrayList<ListItem>();
        for(EchoSessionTypeEnum status : EchoSessionTypeEnum.values()) {
            ListItem element = new ListItem(status.getText(), status.getDisplayText());
            if (status.getText().equals(statusText)){
                element.setSelected(true);
            }
            list.add(element);
        }
        return list;

    }
    public static EchoSessionTypeEnum fromString(String text) {
        for (EchoSessionTypeEnum status : EchoSessionTypeEnum.values()) {
            if (status.getText().equals(text) ) {
                return status;
            }
        }
        throw new RuntimeException("no customer status " + text);
    }

    public static List<String> toStringList(List<EchoSessionTypeEnum> created) {

        List<String> status = new ArrayList<>();
        for(EchoSessionTypeEnum canvassingStatusEnum : created){
            status.add(canvassingStatusEnum.getText());
        }
        return status;
    }
}
