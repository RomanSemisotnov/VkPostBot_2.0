package bot.services.common;

import java.sql.Time;
import java.util.Calendar;

public class ConvertTimeService {




    public static String timeToHH_MM(Time time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);

        return calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
    }

    public static String timeToHH_MM_SS(Time time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);

        return calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
    }

}
