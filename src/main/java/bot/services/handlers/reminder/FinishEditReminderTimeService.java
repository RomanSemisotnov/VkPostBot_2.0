package bot.services.handlers.reminder;

import bot.annotations.Processing;
import bot.entities.MessageBody;
import bot.entities.User;
import bot.enums.Action;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

@Processing(Action.FINISH_EDIT_REMINDER_TIME)
public class FinishEditReminderTimeService extends BaseHandler {

    private final DateFormat formatter = new SimpleDateFormat("HH:mm");

    @Autowired
    private Pattern timePattern;

    @Override
    @SneakyThrows
    public boolean handle(MessageBody body, User user) {

        String time = body.getText().trim();
        if(!timePattern.matcher(time).matches()){
            vkSenderService.send(VkMessage.builder()
                    .vkId(user.getVkId())
                    .textMessage("Время указанно неккоректно, укажите время в формате ЧЧ:ММ, например : 09:30 или 19:55")
                    .build());
            return false;
        }

        user.getReminder().setTime(new Time(formatter.parse(time).getTime()));
        sessionFactory.getCurrentSession().update(user.getReminder());

        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage("Успешно обновлено")
                .build());
        return true;
    }



}
