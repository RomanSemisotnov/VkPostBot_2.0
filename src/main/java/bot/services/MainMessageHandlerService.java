package bot.services;

import bot.entities.User;
import bot.services.handlers.BaseHandler;
import bot.storages.ActionHandlersStorage;
import bot.enums.Action;
import bot.entities.MessageBody;
import bot.repositories.UserRepository;
import bot.storages.LastActionStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class MainMessageHandlerService {


    @Autowired
    private DefineActionService defineActionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActionHandlersStorage actionHandlersStorage;

    @Autowired
    private LastActionStorage lastActionStorage;

    @Transactional
    public void handle(MessageBody message){
        BaseHandler handler = null;
        boolean isSuccess = false;

        User user = userRepository.findByVkId(message.getUserVkId());
        if(user != null){
            userRepository.updateLastDateMessage(user.getId(), Instant.now().getEpochSecond());

            Action prevAction = lastActionStorage.get(user.getId());
            handler = actionHandlersStorage.getHandler(defineActionService.define(message, prevAction));

            isSuccess = handler.handle(message, user);
        } else {
            user = userRepository.save(User.builder().vkId(message.getUserVkId()).build());

            handler = actionHandlersStorage.getHandler(Action.ASK_NICKNAME);

            isSuccess = handler.handle(message, user);
        }

        if(isSuccess)
            lastActionStorage.put(user.getId(), handler.getProcessingAction());

    }


}
