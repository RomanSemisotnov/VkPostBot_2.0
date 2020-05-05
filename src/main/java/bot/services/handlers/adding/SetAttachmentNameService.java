package bot.services.handlers.adding;

import bot.annotations.Processing;
import bot.entities.*;
import bot.enums.Action;
import bot.repositories.TopicRepository;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;

import java.util.List;

@Processing(Action.SET_ATTACHMENT_NAME)
public class SetAttachmentNameService extends BaseHandler {

    @Override
    public boolean handle(MessageBody body, User user) {
        System.out.println("Назначает вложения имя");

        String name = body.getText().trim();
        if(name.equals("")){
            vkSenderService.send(VkMessage.builder()
                    .vkId(user.getVkId())
                    .textMessage("Введите непустое сообщение")
                    .build()
            );
            return false;
        }

        if(attachmentRepository.findWithSameName(user.getId(), name) != null){
            vkSenderService.send(VkMessage.builder()
                    .vkId(user.getVkId())
                    .textMessage("Вы уже сохраняли вложение с таким именем")
                    .build()
            );
            return false;
        }

        Attachment newAttachment = lastIncomingAttachmentStorage.get(user.getId());
        Topic topicWithoutName = topicRepository.getWithoutName(user.getId());
        newAttachment.setName(name);
        newAttachment.setTopicId(topicWithoutName.getId());
        lastIncomingAttachmentStorage.put(user.getId(), attachmentRepository.save(newAttachment));

        String message = "Укажите тему, к которой хотите отнести этот пост, " +
                "если такой темы нету, " +
                "то добавте новую тему след. образом: '" + Action.ADD_TOPIC_BY_COMMAND.getCommand() + " <НАЗВАНИЕ ТЕМЫ> '";

        List<Topic> topics = topicRepository.findByUserIdOrderByName(user.getId());
        topicWithoutNameToEnd(topics, user.getId());

        Keyboard keyboard = Keyboard.ofTopics(topics);
        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage(message)
                .keyboard(keyboard)
                .build()
        );
        return true;
    }

    public void topicWithoutNameToEnd(List<Topic> topics, Integer userId){
        int index = topics.indexOf(Topic.builder().userId(userId).name(TopicRepository.withoutName).build());

        if(index != -1){
            topics.add(topics.remove(index));
        }
    }

}
