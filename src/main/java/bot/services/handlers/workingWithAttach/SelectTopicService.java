package bot.services.handlers.workingWithAttach;

import bot.annotations.Processing;
import bot.entities.Keyboard;
import bot.entities.MessageBody;
import bot.entities.Topic;
import bot.entities.User;
import bot.enums.Action;
import bot.repositories.TopicRepository;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;

import java.util.List;

@Processing(Action.SELECT_TOPIC_BY_INDEX)
public class SelectTopicService extends BaseHandler {


    @Override
    public boolean handle(MessageBody body, User user) {
        System.out.println("Берем вложение по индексу");
        int index;
        try{
            index = Integer.parseInt(body.getText().trim());
        } catch (NumberFormatException e){
            vkSenderService.send(VkMessage.builder()
                    .vkId(user.getVkId())
                    .textMessage("Введите корректный номер топика")
                    .build());
            return false;
        }
        List<Topic> topics = topicRepository.findByUserIdOrderByName(user.getId());

        filterTopicWithoutName(topics, user.getId());

        if(index < 1 || index > topics.size()){
            vkSenderService.send(VkMessage.builder()
                    .vkId(user.getVkId())
                    .textMessage("Такого номера нет в списке")
                    .build());
            return false;
        }

        Topic neededTopic = topics.get(index - 1);

        long notReadAttachmentCount = neededTopic.getAttachments()
                .stream().filter(attachment -> !attachment.isRead()).count();

        Keyboard keyboard = Keyboard.ofActionWithTopic(neededTopic, notReadAttachmentCount != 0);
        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage("В топике находится " +notReadAttachmentCount + " непрочтенных вложений, выберите действие" )
                .keyboard(keyboard)
                .build());
        return true;
    }

    public void filterTopicWithoutName(List<Topic> topics, Integer userId){
        int index = topics.indexOf(Topic.builder().userId(userId).name(TopicRepository.withoutName).build());

        if(index != -1){
            Topic topicWithoutName = topics.remove(index);
            if(topicWithoutName.getAttachments() != null
                    && topicWithoutName.getAttachments().stream().anyMatch(attachment -> !attachment.isRead()))
                topics.add(topicWithoutName);
        }
    }

}
