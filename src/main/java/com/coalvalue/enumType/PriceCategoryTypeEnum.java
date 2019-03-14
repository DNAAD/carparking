package com.coalvalue.enumType;

import com.coalvalue.domain.pojo.ListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silence yuan on 2015/8/18.
 */
public enum PriceCategoryTypeEnum {

/*

    　1、 信汇（Mail Transfer, M/T）
            　2、 电汇（Telegraphic Transfer, T/T）
            　3、 票汇（Demand Draft, D/D）
            4.    付款交单（Document against Payment,D/P）
            5.    承兑交单（Document against acceptance, D/A）
            6.   信用证（Letter of Credit, L/C）

*/


    EX_MINE_PRICE_EXC_VAT ("ex-mine price exc. VAT","出矿不含税", 6,"","出矿不含税"),
    EX_MINE_PRICE ("ex-mine price","出矿含税价", 6,"","出矿含税");






    private final String statusText;
    private final String displayText;
    private final Integer id;
    private String tipsMessage;
    private String shortText;

    public String getTipsMessage() {
        return tipsMessage;
    }

    public void setTipsMessage(String tipsMessage) {
        this.tipsMessage = tipsMessage;
    }

    public String getDisplayText() {
        return displayText;
    }

    private PriceCategoryTypeEnum(String statusText, String displayText, Integer id, String tipsMessage, String shortText) {
        this.statusText = statusText;
        this.displayText = displayText;

        this.id = id;
        this.tipsMessage = tipsMessage;
        this.shortText = shortText;
    }
    public String getText() {
        return this.statusText;
    }

    public Integer getId() {
        return this.id;
    }

    public static PriceCategoryTypeEnum fromString(String text) {
        for (PriceCategoryTypeEnum status : PriceCategoryTypeEnum.values()) {
            if (status.getText().equals(text) ) {
                return status;
            }
        }
        System.out.println(" 找不到类型错误 text is :" + text);
        return null;// PriceCategoryTypeEnum.ERROR;
        // throw new RuntimeException("no customer status " + text);
    }
    public static List<ListItem> retriveTypese() {

        List<ListItem> list = new ArrayList<ListItem>();
        for(PriceCategoryTypeEnum status : PriceCategoryTypeEnum.values()) {
            ListItem element = new ListItem(status.getText(),status.getDisplayText());
            list.add(element);
        }

        return list;

    }

    public static List<ListItem> retriveTypese(String scene) {
        List<ListItem> list = new ArrayList<ListItem>();


        if (ContextStateEnum.CONTEXT_SCENE_price_category_type_hengshan_area.name().equals(scene)) {
            ListItem element = new ListItem(EX_MINE_PRICE_EXC_VAT.getText(), EX_MINE_PRICE_EXC_VAT.getDisplayText(), EX_MINE_PRICE_EXC_VAT.getTipsMessage());
            list.add(element);
            element = new ListItem(EX_MINE_PRICE.getText(), EX_MINE_PRICE.getDisplayText(), EX_MINE_PRICE.getTipsMessage());

            list.add(element);






        }
        return list;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }
}
