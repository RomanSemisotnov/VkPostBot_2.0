package bot.services.handlers.reminder;

import bot.annotations.Processing;
import bot.entities.MessageBody;
import bot.entities.User;
import bot.enums.Action;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;

@Processing(Action.START_EDIT_REMINDER_TIME)
public class StartEditReminderTimeService extends BaseHandler {





    @Override
    public boolean handle(MessageBody body, User user) {

        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage("Установленное время: " + user.getReminder().getTime() + ", введите новое время в формате ЧЧ:ММ")
                .build());

        return true;
    }





}
