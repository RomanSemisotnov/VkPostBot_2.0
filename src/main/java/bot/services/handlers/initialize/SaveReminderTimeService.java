package bot.services.handlers.initialize;

import bot.annotations.Processing;
import bot.entities.MessageBody;
import bot.entities.Reminder;
import bot.entities.User;
import bot.enums.Action;
import bot.enums.DaysOfweek;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Processing(Action.SAVE_REMINDER_TIME)
public class SaveReminderTimeService extends BaseHandler {

    private final DateFormat formatter = new SimpleDateFormat("HH:mm");

    private final Pattern timePattern = Pattern.compile("^(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])$");

    @Autowired
    private DaysOfweek daysOfweek;

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

        List<Reminder> reminders = new ArrayList<>();
        for(Map.Entry<Integer, String> day : daysOfweek.getMap().entrySet()){
            reminders.add(Reminder.builder()
                    .userId(user.getId())
                    .time(new Time(formatter.parse(time).getTime()))
                    .dayNumber(day.getKey())
                    .build());
        }

        if(!reminders.isEmpty()){
            reminderRepository.saveAll(reminders);
            reminders.clear();
        }

        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage("Успешно сохранено")
                .build());
        return true;
    }

}
