package bot.deserializers;

import bot.entities.Attachment;
import bot.entities.MessageBody;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MessageBodyDeserializers extends JsonDeserializer<MessageBody> {


    @Autowired
    private ObjectMapper mapper;

    @Override
    public MessageBody deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);

        int vkId = node.get("from_id").asInt();
        String text = node.get("text").asText();
        Long secondAfterUnixAge = node.get("date").asLong();
        String jsonPayload = node.get("payload") != null ? node.get("payload").asText() : "{}";
        Map<String, Object> payload = mapper.readValue(jsonPayload, new TypeReference<HashMap<String, Object>>() {});
        Attachment attachment = mapper.readValue(node.get("attachments").toString(), Attachment.class);

        return MessageBody.builder()
                .userVkId(vkId)
                .text(text)
                .attachments(attachment)
                .secondAfterUnixAge(secondAfterUnixAge)
                .payload(payload)
                .build();
    }

}
