package bot.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TextButton {
    private final String color = "primary";
    private Action action;
}

