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

@Processing(Action.SAVE_FREQUENCY_REMEMBER_AND_ASK_NOT_READ_COUNT)
public class SaveFrequencyRememberAndAskNotReadCountService extends BaseHandler {





    @Override
    public boolean handle(MessageBody body, User user) {
        String frequencyRemember = body.getText().trim();
        if(StringUtils.isNotEmpty(frequencyRemember)){
            user.getStats().setFrequencyRemember(frequencyRemember);
            sessionFactory.getCurrentSession().update(user.getStats());
        }

        Keyboard keyboard = Keyboard.ofTextButtons(List.of("1-3", "3-7", "7-12", "Больше 12"));
        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage("Сколько статей у вас обычно держится непрочитанными?")
                .keyboard(keyboard)
                .build());
        return true;
    }







}
