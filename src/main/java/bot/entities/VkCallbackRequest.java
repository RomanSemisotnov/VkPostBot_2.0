package bot.entities;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VkCallbackRequest {

    private String type;

    private String secret;

    private MessageBody object;

}
