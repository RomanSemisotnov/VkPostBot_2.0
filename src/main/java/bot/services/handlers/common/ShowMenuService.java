package bot.services.handlers.common;

import bot.annotations.Processing;
import bot.entities.Keyboard;
import bot.entities.MessageBody;
import bot.entities.User;
import bot.enums.Action;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;

@Processing(Action.SHOW_MENU)
public class ShowMenuService extends BaseHandler {




    @Override
    public boolean handle(MessageBody body, User user) {

        vkSenderService.send(VkMessage.builder()
                .vkId(user.getId())
                .textMessage("1.Если хотите что-то почитать, нажмите на кнопку снизу.\n" +
                        "2.Для добавления новой темы введите: '" + Action.ADD_TOPIC_BY_COMMAND + " <НАЗВАНИЕ ТЕМЫ> '\n" +
                        "3.Если хотите добавить вложение, просто сбросте мне его в ЛС")
                .keyboard(Keyboard.ofTextButton(Action.GET_TOPICS_BY_COMMAND.getCommand()))
                .build());

        return true;
    }


}
