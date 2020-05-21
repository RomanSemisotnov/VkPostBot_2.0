package bot.enums;

public enum Action {

    //initial chain of actions
    ASK_TURN,
    SAVE_TURN_AND_ASK_PROFESSION,
    SAVE_PROFESSION_AND_ASK_FREQUENCY_REMEMBER,
    SAVE_FREQUENCY_REMEMBER_AND_ASK_NOT_READ_COUNT,
    SAVE_NOT_READ_COUNT_AND_ASK_REMINDER_TIME,
    SAVE_REMINDER_TIME,

    // first actions chain
    ADD_ATTACHMENT,
    SET_ATTACHMENT_NAME,
    SET_ATTACHMENT_TOPIC_BY_KEYBOARD,

    //second actions chain
    GET_TOPICS_BY_COMMAND("/темы"),
    SELECT_TOPIC_BY_INDEX,
    SHOW_ATTACHMENTS, READ_ATTACHMENT, SET_READ_ATTACHMENT,
    START_EDIT_TOPIC, FINISH_EDIT_TOPIC,
    START_DELETE_TOPIC, FINISH_DELETE_TOPIC,

    //third actions chain
    START_EDIT_REMINDER_TIME("/изменить время"),
    FINISH_EDIT_REMINDER_TIME,

    //others
    SHOW_MENU("/меню"),
    ADD_TOPIC_BY_COMMAND("/добавить"),
    UNKNOWN_ACTION;

    private String command;

    Action(){}

    Action(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

}
