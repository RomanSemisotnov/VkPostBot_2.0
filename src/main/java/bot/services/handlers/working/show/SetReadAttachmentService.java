package bot.services.handlers.working.show;

import bot.annotations.Processing;
import bot.entities.MessageBody;
import bot.entities.User;
import bot.enums.Action;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;

@Processing(Action.SET_READ_ATTACHMENT)
public class SetReadAttachmentService extends BaseHandler {



    @Override
    public boolean handle(MessageBody body, User user) {
        System.out.println("Обновляем поле прочитанно у вложения");

        Integer attachmentId = (Integer) body.getPayload().get("attachment_id");
        Boolean isRead = Boolean.parseBoolean((String) body.getPayload().get("isRead"));

        attachmentRepository.updateRead(attachmentId, isRead);

        String message = isRead ? "Успешно обновленно" : "Хотите прочитать чтонибудь другое? Введите /темы";
        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage(message)
                .build());
        return true;
    }



}
