package bot.services.handlers.common;

import bot.annotations.Processing;
import bot.entities.Keyboard;
import bot.entities.MessageBody;
import bot.entities.User;
import bot.enums.Action;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;

import java.util.List;

@Processing(Action.SHOW_MENU)
public class ShowMenuService extends BaseHandler {




    @Override
    public boolean handle(MessageBody body, User user) {

        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage("1.Если хотите что-то почитать или изменить время напоминания, нажмите на одну из кнопок снизу.\n" +
                        "2.Для добавления новой темы введите: '" + Action.ADD_TOPIC_BY_COMMAND.getCommand() + " <НАЗВАНИЕ ТЕМЫ> '\n" +
                        "3.Если хотите добавить вложение, просто пришлите мне его в ЛС")
                .keyboard(Keyboard.ofTextButtons(List.of(
                        Action.GET_TOPICS_BY_COMMAND.getCommand(), Action.START_EDIT_REMINDER_TIME.getCommand()
                )))
                .build());

        return true;
    }


}
