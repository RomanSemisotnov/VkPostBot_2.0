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


    public void handle(MessageBody message){
        User user = userRepository.findOrCreate(message.getUserVkId());

        Action prevAction = lastActionStorage.get(user.getId());

        Action neededAction = defineActionService.define(message, prevAction);

        BaseHandler handler = actionHandlersStorage.getHandler(neededAction);

        boolean isSuccess = handler.handle(message, user);

        if(isSuccess)
            lastActionStorage.put(user.getId(), handler.getProcessingAction());

    }


}
