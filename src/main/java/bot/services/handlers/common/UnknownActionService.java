package bot.services.handlers.common;

import bot.annotations.Processing;
import bot.entities.MessageBody;
import bot.entities.User;
import bot.enums.Action;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;

@Processing(Action.UNKNOWN_ACTION)
public class UnknownActionService extends BaseHandler {

    @Override
    public boolean handle(MessageBody body, User user) {
        System.out.println("обрабатываем неизвестное действие");

        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage("ВАМ чтото ПОДСКАЗАТЬ?) Если хотите добавить вложение, просто скиньте его мне в личку, " +
                        "если хотите посмотреть все существующие темы введите '/темы', " +
                        "если хотите добавить тему, введите '+ <НАЗВАНИЕ ТЕМЫ>'")
                .build());

        return true;
    }

}
