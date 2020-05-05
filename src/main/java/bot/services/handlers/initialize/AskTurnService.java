package bot.services.handlers.initialize;

import bot.annotations.Processing;
import bot.entities.Keyboard;
import bot.entities.MessageBody;
import bot.entities.User;
import bot.enums.Action;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;

@Processing(Action.ASK_TURN)
public class AskTurnService extends BaseHandler {




    @Override
    public boolean handle(MessageBody body, User user) {
        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage("Добро пожаловать, перед началом работы позвольте задать Вам пару вопросов, это нужно для более эффективной работы нашего Бота.")
                .build());
        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage("Для начала скажите, как ты хотите, чтобы к Вам обращались?) Если хотите подругому - введите что угодно сообщением")
                .keyboard(Keyboard.ofTurn(user))
                .build());
        return true;
    }



}
