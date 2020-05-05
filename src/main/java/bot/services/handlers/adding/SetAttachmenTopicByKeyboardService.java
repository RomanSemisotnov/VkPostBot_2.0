package bot.services.handlers.adding;

import bot.annotations.Processing;
import bot.entities.Attachment;
import bot.entities.MessageBody;
import bot.entities.User;
import bot.enums.Action;
import bot.repositories.TopicRepository;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;

@Processing(Action.SET_ATTACHMENT_TOPIC_BY_KEYBOARD)
public class SetAttachmenTopicByKeyboardService extends BaseHandler {



    @Override
    public boolean handle(MessageBody body, User user) {
        System.out.println("Ставим топик для аттачмента");

        Attachment attachment = lastIncomingAttachmentStorage.get(user.getId());
        Integer topicId = (Integer) body.getPayload().get("topic_id");

        String message;
        if(topicId == null){
            topicId = topicRepository.getWithoutName(user.getId()).getId();
            message = "Вложение сохранено в раздел '" + TopicRepository.withoutName + "'";
        }else{
            message = "Успешно сохранено.";
        }

        attachment.setTopicId(topicId);
        sessionFactory.getCurrentSession().update(attachment);

        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage(message)
                .build()
        );
        lastIncomingAttachmentStorage.remove(user.getId());
        return true;
    }


}
