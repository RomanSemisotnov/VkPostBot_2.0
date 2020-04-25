package bot.services.handlers.working.delete;

import bot.annotations.Processing;
import bot.entities.Attachment;
import bot.entities.MessageBody;
import bot.entities.Topic;
import bot.entities.User;
import bot.enums.Action;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Processing(Action.FINISH_DELETE_TOPIC)
public class FinishDeleteTopicService extends BaseHandler {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    @Transactional
    public boolean handle(MessageBody body, User user) {
        System.out.println("Удаляем топик кокнчательно");

        String mode = (String) body.getPayload().get("mode");
        Integer topicId = (Integer) body.getPayload().get("topic_id");

        if(mode.equals("with")){
            topicRepository.deleteById(topicId);
        }else if(mode.equals("without")){
            Topic topicWithoutName = topicRepository.getWithoutName(user.getId());

            Topic deletingTopic = topicRepository.findById(topicId).get();

            List<Integer> attachmentIds = deletingTopic.getAttachments()
                    .stream().map(Attachment::getId).collect(Collectors.toList());

            attachmentRepository.updateAttachmentsTopic(attachmentIds, topicWithoutName.getId());
            topicRepository.deleteById(deletingTopic.getId());
        }

        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage("Успешно")
                .build());
        return true;
    }



}
