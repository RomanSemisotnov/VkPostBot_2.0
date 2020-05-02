package bot.services.handlers.initialize;

import bot.annotations.Processing;
import bot.entities.MessageBody;
import bot.entities.User;
import bot.enums.Action;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;

@Processing(Action.ASK_NICKNAME)
public class AskNicknameService extends BaseHandler {




    @Override
    public boolean handle(MessageBody body, User user) {

        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage("Добро пожаловать, ПРИДУМАТЬ СЮДА ТЕКСТ, как вас величать?")
                .build());


        return true;
    }



}
