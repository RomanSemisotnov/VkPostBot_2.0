package bot.tasks;

import bot.entities.Reminder;
import bot.repositories.ReminderRepository;
import bot.services.vkClient.VkMessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

@Component
@EnableAsync
@EnableScheduling
public class ReminderTask{

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private VkMessageSenderService vkMessageSenderService;

    private final SimpleDateFormat hh_mm_Format = new SimpleDateFormat("hh:mm");

    private final SimpleDateFormat hh_mm_ss_Format = new SimpleDateFormat("hh:mm:ss");


    @Async
    @Scheduled(fixedRate = 2000)
    public void run() throws ParseException {
        String time = hh_mm_Format.format(Calendar.getInstance().getTime());
        System.out.println(time);
        Time currentTime = new Time(hh_mm_ss_Format.parse(time + ":00").getTime());
        System.out.println(currentTime);
        List<Reminder> reminders = reminderRepository.findByTime(currentTime);
        System.out.println(reminders);

      /*  List<Reminder> reminderss = reminderRepository.findAll();
        for(Reminder r : reminderss){
            System.out.println(r.getTime().getTimezoneOffset());
            System.out.println(r.getTime());
        }*/
    }





}
