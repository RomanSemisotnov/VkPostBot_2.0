package bot.services.handlers.working.edit;

import bot.annotations.Processing;
import bot.entities.MessageBody;
import bot.entities.Topic;
import bot.entities.User;
import bot.enums.Action;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;

@Processing(Action.START_EDIT_TOPIC)
public class StartEditTopicService extends BaseHandler {


    @Override
    public boolean handle(MessageBody body, User user) {
        Integer topicId = (Integer) body.getPayload().get("topic_id");

        Topic topic = topicRepository.findById(topicId).get();

        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage("Текущее название: '" + topic.getName() + "', введите новое название")
                .build());

        topicStorage.put(user.getId(), topicId);
        return true;
    }

}
