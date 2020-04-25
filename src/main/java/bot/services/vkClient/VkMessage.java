package bot.services.vkClient;

import bot.entities.Attachment;
import bot.entities.Keyboard;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VkMessage {

    @NonNull
    private Integer vkId;

    private String textMessage;

    private Attachment attachment;

    private Keyboard keyboard;

}
