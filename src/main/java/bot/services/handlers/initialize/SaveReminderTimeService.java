package bot.services.handlers.initialize;

import bot.annotations.Processing;
import bot.entities.MessageBody;
import bot.entities.Reminder;
import bot.entities.User;
import bot.enums.Action;
import bot.enums.DaysOfweek;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;
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
    public boolean handle(MessageBody body, User user) {
        String[] times = body.getText().split(",");

        Time t;
        List<Reminder> reminders = new ArrayList<>();
        List<String> formatErrors = new ArrayList<>();
        for(String time : times){
            try {
                t = new Time(formatter.parse(time).getTime());

                if(!timePattern.matcher(time.trim()).matches())
                    throw new IllegalArgumentException();

                for(Map.Entry<Integer, String> day : daysOfweek.getMap().entrySet()){
                    reminders.add(Reminder.builder()
                            .userId(user.getId())
                            .time(t)
                            .dayNumber(day.getKey())
                            .build());
                }
            } catch (Exception e) {
                formatErrors.add(time);
            }
            if(!reminders.isEmpty()){
                reminderRepository.saveAll(reminders);
                reminders.clear();
            }
        }

        String message;
        if(!formatErrors.isEmpty()){
            message = "Время: " + String.join(", ", formatErrors) + " добавленно не будет, т.к введенно не корректно";
        }else{
            message = "Успешно сохраненно";
        }

        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage(message)
                .build());
        return true;
    }


}
