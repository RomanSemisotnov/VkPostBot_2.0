package bot.services.handlers.common;

import bot.annotations.Processing;
import bot.entities.MessageBody;
import bot.entities.User;
import bot.enums.Action;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;

@Processing(Action.UNKNOWN_ACTION)
public class UnknownActionService extends BaseHandler {

    String message1 = "Вам что-то подсказать?) Введите '/меню' для справки";

    String message2 = "Чем я могу Вам помочь?) Введите '/меню' для справки";

    @Override
    public boolean handle(MessageBody body, User user) {
        System.out.println("обрабатываем неизвестное действие");

        double rand = (Math.random() * ((2 - 1) + 1)) + 1;

        if(rand == 1){
            vkSenderService.send(VkMessage.builder()
                    .vkId(user.getVkId())
                    .textMessage(message1)
                    .build());
        }else{
            vkSenderService.send(VkMessage.builder()
                    .vkId(user.getVkId())
                    .textMessage(message2)
                    .build());
        }

        return true;
    }

}
