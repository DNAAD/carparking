package com.coalvalue.enumType;


import com.coalvalue.domain.pojo.ListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silence on 2016/1/18.
 */
public enum PricingStrategyEnum {


    SpotPrice ("SpotPrice", "现货价",2,""),
    LongTermSupplyPrice ("LongTermSupplyPrice", "长期供货协议价格",2,""),

 ;// 请求结算






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

    private PricingStrategyEnum(String statusText, String displayText, Integer id, String helpMessage) {
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
        for (PricingStrategyEnum status : PricingStrategyEnum.values()) {
            ListItem element = new ListItem(status.getText(), status.getDisplayText(), status.getHelpMessage());
            list.add(element);
        }

        return list;

    }


    public static List<ListItem> retriveTypese(String statusText) {

        List<ListItem> list = new ArrayList<ListItem>();
        for(PricingStrategyEnum status : PricingStrategyEnum.values()) {
            ListItem element = new ListItem(status.getText(), status.getDisplayText());
            if (status.getText().equals(statusText)){
                element.setSelected(true);
            }
            list.add(element);
        }
        return list;

    }

    public static List<ListItem> retriveTypese(PricingStrategyEnum status) {

        List<ListItem> list = new ArrayList<ListItem>();

        ListItem element = new ListItem(status.getText(),status.getDisplayText());
        list.add(element);

        return list;

    }
    public static PricingStrategyEnum fromString(String text) {
        for (PricingStrategyEnum status : PricingStrategyEnum.values()) {
            if (status.getText().equals(text) ) {
                return status;
            }
        }
        throw new RuntimeException("no customer status " + text);
    }

    public static List<String> toStringList(List<PricingStrategyEnum> created) {

        List<String> status = new ArrayList<>();
        for(PricingStrategyEnum canvassingStatusEnum : created){
            status.add(canvassingStatusEnum.getText());
        }
        return status;
    }


}
