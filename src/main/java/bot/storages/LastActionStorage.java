package bot.storages;

import bot.enums.Action;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class LastActionStorage {

    public ConcurrentHashMap<Integer, Action> storage = new ConcurrentHashMap<>();

    public Action put(Integer userId, Action action){
        return storage.put(userId, action);
    }

    public Action get(Integer userId){
        return storage.get(userId);
    }

    public Action remove(Integer userId){
        return storage.remove(userId);
    }

}
