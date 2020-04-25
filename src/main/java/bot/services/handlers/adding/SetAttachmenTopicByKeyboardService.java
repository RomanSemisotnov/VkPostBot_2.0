package bot.services.handlers.adding;

import bot.annotations.Processing;
import bot.entities.Attachment;
import bot.entities.MessageBody;
import bot.entities.User;
import bot.enums.Action;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;
import org.springframework.transaction.annotation.Transactional;

@Processing(Action.SET_ATTACHMENT_TOPIC_BY_KEYBOARD)
public class SetAttachmenTopicByKeyboardService extends BaseHandler {



    @Override
    @Transactional
    public boolean handle(MessageBody body, User user) {
        System.out.println("Ставим топик для аттачмента");

        Integer topicId = (Integer) body.getPayload().get("topic_id");
        Attachment attachment = lastIncomingAttachmentStorage.remove(user.getId());

        attachmentRepository.updateAttachmentTopic(attachment.getId(), topicId);

        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage("Успешно сохраненно.")
                .build()
        );
        return true;
    }


}
