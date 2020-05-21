package bot.tasks;

import bot.entities.Reminder;
import bot.repositories.ReminderRepository;
import bot.services.vkClient.VkMessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;

@Component
public class ReminderTask extends TimerTask {


    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private VkMessageSenderService vkMessageSenderService;

    @Override
    public void run() {
    /*    String time = new SimpleDateFormat("hh:mm").format(Calendar.getInstance().getTime());

        List<Reminder> reminders = reminderRepository.findByTime(time);*/


    }





}
