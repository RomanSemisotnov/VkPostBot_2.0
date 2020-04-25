package bot.storages;

import bot.entities.Attachment;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class LastIncomingAttachmentStorage {

    public ConcurrentHashMap<Integer, Attachment> storage = new ConcurrentHashMap<>();

    public Attachment put(Integer userId, Attachment attachment){
        return storage.put(userId, attachment);
    }

    public Attachment get(Integer userId){
        return storage.get(userId);
    }

    public Attachment remove(Integer userId){
        return storage.remove(userId);
    }

}
