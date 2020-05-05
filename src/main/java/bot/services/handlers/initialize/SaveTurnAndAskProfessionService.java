package bot.services.handlers.initialize;

import bot.annotations.Processing;
import bot.entities.Keyboard;
import bot.entities.MessageBody;
import bot.entities.User;
import bot.enums.Action;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;

import java.util.List;
import java.util.regex.Pattern;

@Processing(Action.SAVE_TURN_AND_ASK_PROFESSION)
public class SaveTurnAndAskProfessionService extends BaseHandler {

    private final Pattern privatePattern = Pattern.compile("^Привет");

    private final Pattern zdravPattern = Pattern.compile("^Здравст");

    @Override
    public boolean handle(MessageBody body, User user) {

        String nickName = body.getText().trim();
        if(nickName.isEmpty()){
            vkSenderService.send(VkMessage.builder()
                    .vkId(user.getVkId())
                    .textMessage("Вы так и не сказали как называть Вас, может быть 'Сэр', 'Милорд'?")
                    .build());
            return false;
        }

        boolean isDefault = body.getPayload() != null && Boolean.parseBoolean((String) body.getPayload().get("isDefault"));

        if(isDefault){
            user.setCustomTurnFlag(false);
            user.setDefaultTurn(body.getText().split(",")[0] + ", ");
        }else{
            user.setCustomTurnFlag(true);
            user.setCustomTurn(body.getText().trim());
        }
        sessionFactory.getCurrentSession().update(user);

        Keyboard keyboard = Keyboard.ofTextButtons(List.of("Маркетолог", "Дизайнер", "Программист"));
        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage("Укажите пожалуйста чем вы занимаетесь, если это что-то другое, введите текстом")
                .keyboard(keyboard)
                .build());
        return true;
    }



}
