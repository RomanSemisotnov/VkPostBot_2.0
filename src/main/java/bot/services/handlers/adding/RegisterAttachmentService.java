package bot.services.handlers.adding;

import bot.entities.User;
import bot.annotations.Processing;
import bot.entities.Attachment;
import bot.entities.MessageBody;
import bot.enums.Action;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;

@Processing(Action.ADD_ATTACHMENT)
public class RegisterAttachmentService extends BaseHandler {

    @Override
    public boolean handle(MessageBody body, User user) {
        System.out.println("Обработка добавления attachment");
        Attachment newAttachment = body.getAttachments();

        Attachment identicalAttachment = attachmentRepository.findSameAttachmentAtUser(user.getId(), newAttachment.getVkIdentifier());
        if(identicalAttachment != null){
            vkSenderService.send(VkMessage.builder()
                    .vkId(user.getVkId())
                    .textMessage("Такое вложение уже сохраненно!")
                    .build());
            return false;
        }

        lastIncomingAttachmentStorage.put(user.getId(), newAttachment);

        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage("Как вы хотите назвать данное вложение?")
                .build());
        return true;
    }

}
