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

@Processing(Action.SAVE_NOT_READ_COUNT_AND_ASK_REMINDER_TIME)
public class SaveNotReadCountAndAskReminderTimeService extends BaseHandler {



    @Override
    public boolean handle(MessageBody body, User user) {
        String notReadCount = body.getText().trim();
        if(StringUtils.isNotEmpty(notReadCount)){
            user.getStats().setNotReadCount(notReadCount);
            sessionFactory.getCurrentSession().update(user.getStats());
        }

        vkSenderService.send(VkMessage.builder()
                .vkId(user.getVkId())
                .textMessage(user.getName() + ", по умолчанию я буду напоминать Вам каждый день о вложениях, которые Вы сохраняете " +
                        "(эту настройку можно будет изменить), выберите время в которое я буду писать Вам, " +
                        "если хотите указать свое время, то просто напишите мне его в формате ЧЧ:ММ ")
                .keyboard(Keyboard.ofTextButtons(List.of("13:30", "19:30", "21:30")))
                .build());
        return true;
    }





}
