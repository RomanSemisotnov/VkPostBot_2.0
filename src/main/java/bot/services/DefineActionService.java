package bot.services;

import bot.enums.Action;
import bot.entities.MessageBody;
import bot.storages.ActionChainStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class DefineActionService {

    @Autowired
    private ActionChainStorage actionChainStorage;

    @Autowired
    private Pattern anyCommandPattern;

    @Autowired
    private Pattern addTopicCommandPattern;

    @Autowired
    private Pattern getTopicCommandPattern;

    @Autowired
    private Pattern getEditReminderPattern;

    @Autowired
    private Pattern getMenuPattern;

    public Action define(MessageBody body, Action prevAction) {
        if (body.getAttachments() != null) // we have attachment
            return Action.ADD_ATTACHMENT;

        if (anyCommandPattern.matcher(body.getText()).matches()) {  // we got any command
            String command = body.getText().toLowerCase().trim();
            if(addTopicCommandPattern.matcher(command).matches())
                return Action.ADD_TOPIC_BY_COMMAND;

            if (getTopicCommandPattern.matcher(command).matches())
                return Action.GET_TOPICS_BY_COMMAND;

            if(getMenuPattern.matcher(command).matches())
                return Action.SHOW_MENU;

            if(getEditReminderPattern.matcher(command).matches())
                return Action.START_EDIT_REMINDER_TIME;
        }

        if (body.getPayload() != null && body.getPayload().containsKey("neededAction")) // we got message from keyboard
            return Action.valueOf((String) body.getPayload().get("neededAction"));

        if(prevAction != null)
            return actionChainStorage.nextAction(prevAction);

        return Action.UNKNOWN_ACTION;
    }

}
