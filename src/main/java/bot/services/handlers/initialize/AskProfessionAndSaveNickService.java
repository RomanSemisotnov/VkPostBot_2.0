package bot.services.handlers.initialize;

import bot.annotations.Processing;
import bot.entities.Keyboard;
import bot.entities.MessageBody;
import bot.entities.User;
import bot.enums.Action;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;

import java.util.List;

@Processing(Action.ASK_PROFESSION_AND_SAVE_NICK)
public class AskProfessionAndSaveNickService extends BaseHandler {



    @Override
    public boolean handle(MessageBody body, User user) {

        String nickName = body.getText().trim();
        if(nickName.equals("")){
            vkSenderService.send(VkMessage.builder()
                    .vkId(user.getVkId())
                    .textMessage("Вы так и не сказали как называть Вас, может быть 'Сэр', 'Милорд'?")
                    .build());
            return false;
        }

        userRepository.updateNickName(user.getId(), nickName);

        Keyboard keyboard = Keyboard.ofProfession(List.of("Маркетолог", "Дизайнер", "Программист"));
        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage("Укажите пожалуйста чем вы занимаетесь, если это что-то другое, введите текстом")
                .keyboard(keyboard)
                .build());
        return true;
    }



}
