package bot.services.vkClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VkMessageSenderService extends AbstractMessageSenderService {

    @Autowired
    private ObjectMapper mapper;

    public void send(VkMessage message) {
        try {
            MessagesSendQuery query = vkApiClient.messages().send(myGroupActor);
            query.userId(message.getVkId());

            if (message.getTextMessage() != null)
                query.message(message.getTextMessage());

            if (message.getAttachment() != null)
                query.attachment(message.getAttachment().getType() + message.getAttachment().getOwnerId() + "_" + message.getAttachment().getVkIdentifier());

            if (message.getKeyboard() != null)
                query.unsafeParam("keyboard", mapper.writeValueAsString(message.getKeyboard()));

            query.execute();
        } catch (ApiException | ClientException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
