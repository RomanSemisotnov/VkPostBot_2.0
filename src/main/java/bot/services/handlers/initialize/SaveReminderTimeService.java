package bot.services.handlers.initialize;

import bot.annotations.Processing;
import bot.entities.MessageBody;
import bot.entities.Reminder;
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

@Processing(Action.SAVE_REMINDER_TIME)
public class SaveReminderTimeService extends BaseHandler {

    private final DateFormat formatter = new SimpleDateFormat("HH:mm");

    @Autowired
    private Pattern timePattern;

   // @Autowired
   // private DaysOfweek daysOfweek;

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

        /*List<Reminder> reminders = new ArrayList<>();
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
        }*/

        reminderRepository.save(Reminder.builder()
                .userId(user.getId())
                .time(new Time(formatter.parse(time).getTime()))
                .build());

        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage("Успешно сохранено")
                .build());
        return true;
    }

}
