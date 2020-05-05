package bot.services.handlers.workingWithAttach.show;

import bot.annotations.Processing;
import bot.entities.Attachment;
import bot.entities.Keyboard;
import bot.entities.MessageBody;
import bot.entities.User;
import bot.enums.Action;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;

import java.util.List;

@Processing(Action.READ_ATTACHMENT)
public class ReadAttachmentService extends BaseHandler {



    @Override
    public boolean handle(MessageBody body, User user) {
        System.out.println("Читаем вложение");

        int attachmentIndex;
        try{
            attachmentIndex = Integer.parseInt(body.getText().trim());
        } catch (NumberFormatException e){
            vkSenderService.send(VkMessage.builder()
                    .vkId(user.getVkId())
                    .textMessage("Введите корректный номер вложения")
                    .build());
            return false;
        }

        List<Attachment> attachments = attachmentRepository.findByTopicIdAndIsRead(topicStorage.get(user.getId()), false);
        if(attachmentIndex < 1 || attachmentIndex > attachments.size()){
            vkSenderService.send(VkMessage.builder()
                    .vkId(user.getVkId())
                    .textMessage("Такого номера нет в списке")
                    .build());
            return false;
        }
        Attachment neededAttachment = attachments.get(attachmentIndex - 1);

        Keyboard keyboard = Keyboard.ofSetReadStatusSpecificAttachment(neededAttachment);
        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .attachment(neededAttachment)
                .keyboard(keyboard)
                .build());
        topicStorage.remove(user.getId());
        return true;
    }



}
