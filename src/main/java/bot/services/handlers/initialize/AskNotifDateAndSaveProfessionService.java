package bot.services.handlers.initialize;

import bot.annotations.Processing;
import bot.entities.MessageBody;
import bot.entities.User;
import bot.enums.Action;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;

@Processing(Action.ASK_NOTIFICATION_TIME_AND_SAVE_PROFESSION)
public class AskNotifDateAndSaveProfessionService extends BaseHandler {





    @Override
    public boolean handle(MessageBody body, User user) {

        String profession = body.getText().trim();
        if(!profession.equals("")){
            userRepository.updateProfession(user.getId(), profession);
        }

        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage("В какое время вам удобно читать закладки?")
                .build());
        return true;
    }




}
