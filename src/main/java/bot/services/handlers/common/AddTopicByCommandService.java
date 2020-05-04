package bot.services.handlers.common;

import bot.annotations.Processing;
import bot.entities.Attachment;
import bot.entities.MessageBody;
import bot.entities.Topic;
import bot.entities.User;
import bot.enums.Action;
import bot.repositories.TopicRepository;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;

import java.util.regex.Pattern;

@Processing(Action.ADD_TOPIC_BY_COMMAND)
public class AddTopicByCommandService extends BaseHandler {

    private final Pattern addPattern = Pattern.compile(Action.ADD_TOPIC_BY_COMMAND.getCommand());

    @Override
    public boolean handle(MessageBody body, User user) {
        System.out.println("Добавление топика по команде");

        String topicName = addPattern.matcher(body.getText()).replaceAll("").trim();
        if(topicName.equals(TopicRepository.withoutName)){
            vkSenderService.send(VkMessage.builder()
                    .vkId(user.getVkId())
                    .textMessage("Топик с данным названием не может быть создан")
                    .build());
            return false;
        }

        if(topicRepository.findByUserIdAndName(user.getId(), topicName) != null){
            vkSenderService.send(VkMessage.builder()
                    .vkId(user.getVkId())
                    .textMessage("Топик с таким названием уже существует")
                    .build());
            return false;
        }

        Topic topic = topicRepository.save(Topic.builder().userId(user.getId()).name(topicName).build());
        Attachment attachment = lastIncomingAttachmentStorage.get(user.getId());

        String msg;
        if(attachment != null){
            attachmentRepository.updateAttachmentTopic(attachment.getId(), topic.getId());

            msg = "Вложение и топик успешно сохраненны.";
        } else {
            msg = "Топик успешно сохранен.";
        }

        lastIncomingAttachmentStorage.remove(user.getId());
        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage(msg)
                .build());
        return true;
    }


}
