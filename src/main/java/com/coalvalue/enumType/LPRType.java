package com.coalvalue.enumType;

import com.coalvalue.domain.pojo.ListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silence on 2016/1/18.
 */
public enum LPRType {



    未知车牌 ("未知车牌","未知车牌", 0,""),

    蓝牌小汽车 ("蓝牌小汽车","蓝牌小汽车", 1,""),

    黑牌小汽车 ("黑牌小汽车","黑牌小汽车", 2,""),

    单排黄牌 ("单排黄牌","单排黄牌", 3,""),
    双排黄牌 ("双排黄牌","双排黄牌", 4,""),
    警车车牌 ("警车车牌","警车车牌", 5,""),



    武警车牌 ("武警车牌","武警车牌", 6,""),

    个性化车牌 ("个性化车牌","个性化车牌", 7,""),

    单排军车牌 ("单排军车牌","单排军车牌", 8,""),

    双排军车牌 ("双排军车牌","双排军车牌", 9,""),
    使馆车牌 ("使馆车牌","使馆车牌", 10,""),


    香港进出中国大陆车牌 ("香港进出中国大陆车牌","香港进出中国大陆车牌", 11,""),

    农用车牌 ("农用车牌","农用车牌", 12,""),
    练车牌 ("练车牌","练车牌", 13,""),
    澳门进出中国大陆车牌 ("澳门进出中国大陆车牌","澳门进出中国大陆车牌", 14,""),


    双层武警车牌 ("双层武警车牌","双层武警车牌", 15,""),
    武警总队车牌 ("武警总队车牌","武警总队车牌", 16,""),

    双层武警总队车牌 ("双层武警总队车牌","双层武警总队车牌", 17,""),
    民航 ("民航","民航", 18,""),
    新能源 ("新能源","新能源", 19,"");




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

    private LPRType(String statusText, String displayText, Integer id, String helpMessage) {
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
        for (LPRType status : LPRType.values()) {
            ListItem element = new ListItem(status.getText(), status.getDisplayText(), status.getHelpMessage());
            list.add(element);
        }

        return list;

    }


    public static List<ListItem> retriveTypese(String statusText) {

        List<ListItem> list = new ArrayList<ListItem>();
        for(LPRType status : LPRType.values()) {
            ListItem element = new ListItem(status.getText(), status.getDisplayText());
            if (status.getText().equals(statusText)){
                element.setSelected(true);
            }
            list.add(element);
        }
        return list;

    }
    public static LPRType fromString(String text) {
        for (LPRType status : LPRType.values()) {
            if (status.getText().equals(text) ) {
                return status;
            }
        }
        throw new RuntimeException("no customer status " + text);
    }

    public static List<String> toStringList(List<LPRType> created) {

        List<String> status = new ArrayList<>();
        for(LPRType canvassingStatusEnum : created){
            status.add(canvassingStatusEnum.getText());
        }
        return status;
    }
}
