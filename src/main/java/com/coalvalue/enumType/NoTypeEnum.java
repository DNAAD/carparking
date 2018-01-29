package com.coalvalue.enumType;

import com.coalvalue.configuration.CommonConstant;
import com.coalvalue.domain.pojo.ListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silence on 2016-07-12.
 */
public enum NoTypeEnum {



    CANVASSING (CommonConstant.NO_TYPE_CANVASSING,"司机揽货号",1,""), ORDER(CommonConstant.NO_TYPE_ORDER,"订单号",1,""), DEAL(CommonConstant.NO_TYPE_DEAL,"订单号",1,"")
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

    private NoTypeEnum(String statusText, String displayText, Integer id, String helpMessage) {
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
        for (NoTypeEnum status : NoTypeEnum.values()) {
            ListItem element = new ListItem(status.getId(), status.getDisplayText(), status.getHelpMessage());
            list.add(element);
        }

        return list;

    }


    public static List<ListItem> retriveTypese(String statusText) {

        List<ListItem> list = new ArrayList<ListItem>();
        for(NoTypeEnum status : NoTypeEnum.values()) {
            ListItem element = new ListItem(status.getText(), status.getDisplayText());
            if (status.getText().equals(statusText)){
                element.setSelected(true);
            }
            list.add(element);
        }
        return list;

    }
    public static NoTypeEnum fromString(String text) {
        for (NoTypeEnum status : NoTypeEnum.values()) {
            if (status.getText().equals(text) ) {
                return status;
            }
        }
        System.out.println(" 找不到类型错误 text is :" + text);
        throw new RuntimeException("no customer status " + text);


    }
}
