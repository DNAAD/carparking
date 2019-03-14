package com.coalvalue.enumType;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by silence on 2016/1/18.
 */
public enum CommandEnum {

    clear ("clear","clear", 2,""),
    bind ("bind","bind", 2,""),
    shell_command ("shell_command","shell_command", 2,""),
    shell__ ("shell__","shell__", 2,""),
    unbind ("unbind","unbind", 2,""),
    image ("image","image", 2,""),
    shell_script_command ("shell_script_command","shell_script_command", 2,""),       ;



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

    private CommandEnum(String statusText, String displayText, Integer id, String helpMessage) {
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


    public static CommandEnum fromString(String text) {
        for (CommandEnum status : CommandEnum.values()) {
            if (status.getText().equals(text) ) {
                return status;
            }
        }
        throw new RuntimeException("no customer status " + text);
    }

    public static List<String> toStringList(List<CommandEnum> created) {

        List<String> status = new ArrayList<>();
        for(CommandEnum canvassingStatusEnum : created){
            status.add(canvassingStatusEnum.getText());
        }
        return status;
    }
}
