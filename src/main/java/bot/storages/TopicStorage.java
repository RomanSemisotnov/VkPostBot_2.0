package bot.storages;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class TopicStorage {

    public ConcurrentHashMap<Integer, Integer> storage = new ConcurrentHashMap<>();

    public Integer put(Integer userId, Integer topicId){
        return storage.put(userId, topicId);
    }

    public Integer get(Integer userId){
        return storage.get(userId);
    }

    public Integer remove(Integer userId){
        return storage.remove(userId);
    }

}
