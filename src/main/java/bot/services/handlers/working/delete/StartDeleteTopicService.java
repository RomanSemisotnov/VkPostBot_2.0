package bot.services.handlers.working.delete;

import bot.annotations.Processing;
import bot.entities.*;
import bot.enums.Action;
import bot.repositories.TopicRepository;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;

import java.util.List;

@Processing(Action.START_DELETE_TOPIC)
public class StartDeleteTopicService extends BaseHandler {


    @Override
    public boolean handle(MessageBody body, User user) {
        System.out.println("Начинаем удалять топик");
        Integer topicId = (Integer) body.getPayload().get("topic_id");

        Topic topic  = topicRepository.findById(topicId).get();

        List<Attachment> attachments = topic.getAttachments();
        if(attachments.isEmpty() || topic.getName().equals(TopicRepository.withoutName)){
            topicRepository.deleteById(topicId);
            vkSenderService.send(VkMessage.builder()
                    .vkId(user.getVkId())
                    .textMessage("Топик успешно удален")
                    .build());
            return false;
        }

        String msg = "В топике имеется " + attachments.size() + " вложения, удалить вместе с ними или перенести вложения в раздел 'Без темы'";
        Keyboard keyboard = Keyboard.ofKindDeleteTopic(topic);
        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage(msg)
                .keyboard(keyboard)
                .build());
        return true;
    }

}
