package bot.entities;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Action {
    private final String type = "text";
    private String label;
    private Map<String, Object> payload;
}
