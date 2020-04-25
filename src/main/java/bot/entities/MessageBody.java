package bot.entities;

import bot.deserializers.MessageBodyDeserializers;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = MessageBodyDeserializers.class)
public class MessageBody {

    private int userVkId;   // vk_id
    private String text;
    private Map<String, Object> payload;
    private Attachment attachments;

}
