package bot.services.handlers.working.show;

import bot.annotations.Processing;
import bot.entities.Attachment;
import bot.entities.MessageBody;
import bot.entities.User;
import bot.enums.Action;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Processing(Action.SHOW_ATTACHMENTS)
public class ShowAttachmentsService extends BaseHandler {



    @Override
    @Transactional
    public boolean handle(MessageBody body, User user) {
        System.out.println("Показываем все непрочитанные топики");
        Integer neededTopic = (Integer) body.getPayload().get("topic_id");

        List<Attachment> attachments = attachmentRepository.findByTopicIdAndIsRead(neededTopic, false);

        StringBuilder message = new StringBuilder("Выберите вложение, которое хотите прочитать, введя его порядковый номер: \n");
        int l = attachments.size();
        for(int i = 0; i < l; i++){
            message.append(i+1).append(". ").append(attachments.get(i).getName()).append("\n");
        }

        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage(message.toString())
                .build());
        topicStorage.put(user.getId(), neededTopic);
        return true;
    }



}
