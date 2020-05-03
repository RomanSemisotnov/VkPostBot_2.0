package bot.enums;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class DaysOfweek {

    @Getter
    private final Map<Integer, String> map;

    public DaysOfweek(){
        map = new LinkedHashMap<>();
        map.put(1, "Понедельник");
        map.put(2, "Вторник");
        map.put(3, "Среда");
        map.put(4, "Четверг");
        map.put(5, "Пятница");
        map.put(6, "Суббота");
        map.put(7, "Воскресенье");
    }

}
