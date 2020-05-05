package bot.services;

import bot.entities.User;
import bot.entities.UserStats;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkUserInfoService;
import bot.storages.ActionHandlersStorage;
import bot.enums.Action;
import bot.entities.MessageBody;
import bot.repositories.UserRepository;
import bot.storages.LastActionStorage;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class MainMessageHandlerService {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private DefineActionService defineActionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActionHandlersStorage actionHandlersStorage;

    @Autowired
    private LastActionStorage lastActionStorage;

    @Autowired
    private VkUserInfoService vkUserInfoService;

    @Transactional
    public void handle(MessageBody message){
        BaseHandler handler = null;
        boolean isSuccess = false;

        User user = userRepository.findByVkId(message.getUserVkId());

        if(user != null){
            user.setLastMessageSec(Instant.now().getEpochSecond());
            sessionFactory.getCurrentSession().update(user);

            Action prevAction = lastActionStorage.get(user.getId());
            handler = actionHandlersStorage.getHandler(defineActionService.define(message, prevAction));

            isSuccess = handler.handle(message, user);
        } else {
            user = User.builder().vkId(message.getUserVkId()).build();
            UserXtrCounters userInfo = vkUserInfoService.get(user.getVkId());
            if(userInfo != null){
                user.setName(userInfo.getFirstName());
            }
            user = userRepository.save(user);
            sessionFactory.getCurrentSession().save(UserStats.builder().userId(user.getId()).build());

            handler = actionHandlersStorage.getHandler(Action.ASK_TURN);

            isSuccess = handler.handle(message, user);
        }

        if(isSuccess)
            lastActionStorage.put(user.getId(), handler.getProcessingAction());

    }


}
