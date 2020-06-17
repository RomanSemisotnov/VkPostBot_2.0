package bot.services.handlers.reminder;

import bot.annotations.Processing;
import bot.entities.MessageBody;
import bot.entities.User;
import bot.enums.Action;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;

import static bot.services.common.ConvertTimeService.timeToHH_MM;

@Processing(Action.START_EDIT_REMINDER_TIME)
public class StartEditReminderTimeService extends BaseHandler {




    @Override
    public boolean handle(MessageBody body, User user) {

        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage("Установленное время: " + timeToHH_MM(user.getReminder().getTime()) + ", введите новое время в формате ЧЧ:ММ")
                .build());

        return true;
    }




}
