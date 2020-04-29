package bot;

import bot.configs.VkConfig;
import bot.entities.VkCallbackRequest;
import bot.services.MainMessageHandlerService;
import bot.storages.LastActionStorage;
import bot.storages.LastIncomingAttachmentStorage;
import bot.storages.TopicStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")
public class CallbackHandlerController {


    @Autowired
    LastActionStorage actions;

    @Autowired
    LastIncomingAttachmentStorage attach;

    @Autowired
    TopicStorage topicStorage;

    @GetMapping
    public Map get(){
        return Map.of("actions", actions.storage, "attachments", attach.storage, "topics", topicStorage.storage);
    }

    @Autowired
    private VkConfig vkConfig;

    @Autowired
    private MainMessageHandlerService mainMessageHandlerService;

    @PostMapping
    public String execute(@RequestBody VkCallbackRequest callback) throws Exception {

        if(!vkConfig.getSecret().equals(callback.getSecret()))
            throw new Exception("secret phrase is not correct");

        if(callback.getType() == null )
            throw new Exception("message must have a type");

        if(callback.getType().equals("message_new"))
            mainMessageHandlerService.handle(callback.getObject());

       return "189ea0f5";
    }

}
