package bot.deserializers;

import bot.entities.Attachment;
import bot.enums.AllowedAttachments;
import bot.exceptions.MoreOneAttachmentException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Iterator;

@Component
public class AttachmentCustomDeserializer extends JsonDeserializer<Attachment> {

    @SneakyThrows
    @Override
    public Attachment deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Iterator<JsonNode> iterator=node.iterator();
        if(!iterator.hasNext())
            return null;

        JsonNode attachmentNode=iterator.next();

        Attachment instance;
        String type = attachmentNode.get("type").asText();
        if (type.toUpperCase().equals(AllowedAttachments.WALL.name())) {
            int vkId = attachmentNode.get(type).get("id").asInt();
            int ownerId = attachmentNode.get(type).get("from_id").asInt();
            instance = Attachment.builder().vkIdentifier(vkId).ownerId(ownerId).type(type).build();
        } else {
            throw new IllegalArgumentException("this attachment type not supported");
        }

        if(iterator.hasNext()) // если больше 1 вложения то это ошибка!
            throw new MoreOneAttachmentException("the message must have no more than one attachment");

        return instance;
    }

}
