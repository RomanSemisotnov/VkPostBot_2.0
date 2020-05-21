package bot.configs;

import bot.entities.Reminder;
import bot.repositories.ReminderRepository;
import bot.services.vkClient.VkMessage;
import bot.services.vkClient.VkMessageSenderService;
import bot.tasks.ReminderTask;
import java.sql.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import static bot.services.common.ConvertTimeService.convertToHH_MM_SS;

@Configuration
public class TasksConfig {

    @Autowired
    private ReminderTask reminderTask;


    @Autowired
    ReminderRepository reminderRepository;

    @Autowired
    VkMessageSenderService vkMessageSenderService;

    @PostConstruct
    public void run(){
       /* Timer timer=new Timer();
        int currentSecond = Integer.parseInt(new SimpleDateFormat("ss").format(Calendar.getInstance().getTime()));

        timer.scheduleAtFixedRate(reminderTask, 60*1000 - (currentSecond * 1000),60*1000);*/


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Time(1589155960385L)); //12 MINUTE
        calendar.get(Calendar.SECOND);
        Time timeZeroSec = new Time(1589155960385L - calendar.get(Calendar.SECOND) * 1000);
        System.out.println(timeZeroSec.getTimezoneOffset());

        List<Reminder> reminders = reminderRepository.findByTime(timeZeroSec);
        for (Reminder reminder : reminders){
            vkMessageSenderService.send(VkMessage.builder()
                    .vkId(reminder.getUser().getVkId())
                    .textMessage("xuy")
                    .build());
        }
    }



}
