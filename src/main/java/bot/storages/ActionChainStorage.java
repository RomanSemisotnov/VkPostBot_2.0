package bot.storages;

import bot.enums.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ActionChainStorage {

    @Bean
    public ActionChain initChain(){
        return new ActionChain(Action.ASK_TURN, Action.SAVE_TURN_AND_ASK_PROFESSION,
                Action.SAVE_PROFESSION_AND_ASK_FREQUENCY_REMEMBER, Action.SAVE_FREQUENCY_REMEMBER_AND_ASK_NOT_READ_COUNT,
                Action.SAVE_NOT_READ_COUNT_AND_ASK_REMINDER_TIME, Action.SAVE_REMINDER_TIME);
    }

    @Bean
    public ActionChain registrAttachmentChain(){
        return new ActionChain(Action.ADD_ATTACHMENT, Action.SET_ATTACHMENT_NAME, Action.SET_ATTACHMENT_TOPIC_BY_KEYBOARD);
    }

    @Bean
    public ActionChain workingWithAttachmentChain(){
        return new ActionChain(Action.GET_TOPICS_BY_COMMAND, Action.SELECT_TOPIC_BY_INDEX);
    }

    @Bean
    public ActionChain editTopicChain(){
        return new ActionChain(Action.START_EDIT_TOPIC, Action.FINISH_EDIT_TOPIC);
    }

    @Bean
    public ActionChain readAttachmentChain(){
        return new ActionChain(Action.SHOW_ATTACHMENTS, Action.READ_ATTACHMENT);
    }

    @Autowired
    private List<ActionChain> chains;

    public Action nextAction(Action prevAction){
        for(ActionChain chain : chains){
            if(chain.contains(prevAction))
                return chain.nextAction(prevAction);
        }

        return Action.UNKNOWN_ACTION;
    }

}

class ActionChain{

    private final List<Action> chain;

    public ActionChain(Action... actions){
        chain = Arrays.asList(actions);
    }

    public Action nextAction(Action prevAction){
        ListIterator<Action> iter = chain.listIterator();

        while(iter.hasNext()){
            Action action = iter.next();
            if(action.equals(prevAction) && iter.hasNext())
                return iter.next();
        }

        return Action.UNKNOWN_ACTION;
    }

    public boolean contains(Action action){
        return chain.contains(action);
    }

}
