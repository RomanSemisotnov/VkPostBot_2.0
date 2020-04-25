package bot.enums;

public enum Action {

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

    ADD_TOPIC_BY_COMMAND("+"),
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
