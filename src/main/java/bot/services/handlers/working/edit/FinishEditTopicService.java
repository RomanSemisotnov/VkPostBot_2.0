package bot.services.handlers.working.edit;

import bot.annotations.Processing;
import bot.entities.MessageBody;
import bot.entities.User;
import bot.enums.Action;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;

@Processing(Action.FINISH_EDIT_TOPIC)
public class FinishEditTopicService extends BaseHandler {



    @Override
    public boolean handle(MessageBody body, User user) {
        String newName = body.getText();

        topicRepository.updateNameById(topicStorage.get(user.getId()), newName);

        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage("Успешно обновленно")
                .build());
        topicStorage.remove(user.getId());
        return true;
    }



}
