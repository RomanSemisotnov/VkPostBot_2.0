package bot.services.handlers.initialize;

import bot.annotations.Processing;
import bot.entities.MessageBody;
import bot.entities.Reminder;
import bot.entities.User;
import bot.enums.Action;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Processing(Action.SAVE_REMINDER_TIME)
public class SaveReminderTimeService extends BaseHandler {

    private final List<Integer> daysOfWeek = List.of(Calendar.SUNDAY, Calendar.MONDAY, Calendar.TUESDAY,
            Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY);

    private final DateFormat formatter = new SimpleDateFormat("HH:mm");

    @Override
    public boolean handle(MessageBody body, User user) {
        String[] dates = body.getText().split(",");

        Time time;
        List<String> errors = new ArrayList<>();
        List<Reminder> reminders = new ArrayList<>();
        for(String date : dates){
            try {
                time = new Time(formatter.parse(date).getTime());

                for(Integer dayNumber : daysOfWeek){
                    reminders.add(Reminder.builder()
                            .userId(user.getId())
                            .time(time)
                            .dayNumber(dayNumber)
                            .build());
                }
            } catch (ParseException e) {
                errors.add(date);
            }
            reminderRepository.saveAll(reminders);
            reminders.clear();
        }

        String message;
        if(!errors.isEmpty()){
            message = "Время: " + String.join(", ", errors) + " введенно неверно, указанные времена добавленны не будут";
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
