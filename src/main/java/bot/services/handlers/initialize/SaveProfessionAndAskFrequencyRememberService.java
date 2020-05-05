package bot.services.handlers.initialize;

import bot.annotations.Processing;
import bot.entities.Keyboard;
import bot.entities.MessageBody;
import bot.entities.User;
import bot.enums.Action;
import bot.services.handlers.BaseHandler;
import bot.services.vkClient.VkMessage;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Processing(Action.SAVE_PROFESSION_AND_ASK_FREQUENCY_REMEMBER)
public class SaveProfessionAndAskFrequencyRememberService extends BaseHandler {




    @Override
    public boolean handle(MessageBody body, User user) {

        String profession = body.getText().trim();
        if(StringUtils.isNotEmpty(profession)){
            user.getStats().setProfession(profession);
            sessionFactory.getCurrentSession().update(user.getStats());
        }

        Keyboard keyboard = Keyboard.ofTextButtons(List.of("Каждый день", "В течение 2-3 дней",
                "Раз в неделю", "Раз в пару недель", "Раз в месяц и больше"));
        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage("Как часто вы вспоминаете о полезных статьях, которые сохранили у себя в личке или где-то еще чтобы потом прочитать?")
                .keyboard(keyboard)
                .build());
        return true;
    }



}
