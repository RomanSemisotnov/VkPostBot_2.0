package bot.services.handlers.workingWithAttach;

import bot.annotations.Processing;
import bot.entities.MessageBody;
import bot.entities.Topic;
import bot.entities.User;
import bot.enums.Action;
import bot.repositories.TopicRepository;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;

import java.util.List;

@Processing(Action.GET_TOPICS_BY_COMMAND)
public class GetTopicsByCommandService extends BaseHandler {


    @Override
    public boolean handle(MessageBody body, User user) {
        System.out.println("получаем все темы");

        List<Topic> topics = topicRepository.findByUserIdOrderByName(user.getId());
        filterTopicWithoutName(topics, user.getId());

        if(topics.isEmpty()){
            vkSenderService.send(VkMessage.builder()
                    .vkId(user.getVkId())
                    .textMessage("Созданные темы отсутствуют, чтобы создать топик, введите '" + Action.SHOW_MENU + " <Название темы>'")
                    .build());
            return false;
        }

        StringBuilder message = new StringBuilder("Выберите тему, введя ее порядковый номер: \n");
        int l = topics.size();
        for(int i = 0; i < l; i++){
            message.append(i+1).append(". ").append(topics.get(i).getName()).append("\n");
        }

        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage(message.toString())
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
