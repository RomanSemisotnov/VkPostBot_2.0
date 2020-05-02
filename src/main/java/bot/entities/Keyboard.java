package bot.entities;

import bot.repositories.TopicRepository;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class Keyboard {

    private static final int DEFAULT_COUNT_BUTTON_ON_LINE = 2;
    private final boolean one_time = true;
    private final boolean inline = false;
    private List<List<TextButton>> buttons;

    private Keyboard(List<List<TextButton>> buttons){
        this.buttons=buttons;
    }

    public static Keyboard ofProfession(List<String> professions){
        List<List<TextButton>> buttons = new ArrayList<>();
        for(String profession : professions){
            buttons.add(List.of(new TextButton( new Action (profession, null))));
        }
        return new Keyboard(buttons);
    }

    public static Keyboard ofSetReadAttachment(Attachment attachment){
        TextButton notRead = new TextButton( new Action ("Я не прочитал", Map.of("attachment_id", attachment.getId(),
                "neededAction", bot.enums.Action.SET_READ_ATTACHMENT, "isRead", "false")));
        TextButton read = new TextButton( new Action ("Я прочитал", Map.of("attachment_id", attachment.getId(),
                "neededAction", bot.enums.Action.SET_READ_ATTACHMENT, "isRead", "true")));

        List<List<TextButton>> buttons = new ArrayList<>();
        buttons.add(List.of(notRead));
        buttons.add(List.of(read));
        return new Keyboard(buttons);
    }

    public static Keyboard ofKindDeleteTopic(Topic topic){
        TextButton deleteWithAttachment = new TextButton( new Action ("Удалить с вложениями", Map.of("topic_id", topic.getId(),
                "neededAction", bot.enums.Action.FINISH_DELETE_TOPIC, "mode", "with" )) );
        TextButton deleteWithoutAttachment = new TextButton( new Action ("Удалить с переносом вложений", Map.of("topic_id", topic.getId(),
                "neededAction", bot.enums.Action.FINISH_DELETE_TOPIC, "mode", "without")) );

        List<List<TextButton>> buttons = new ArrayList<>();
        buttons.add(List.of(deleteWithAttachment));
        buttons.add(List.of(deleteWithoutAttachment));
        return new Keyboard(buttons);
    }

    public static Keyboard ofActionWithTopic(Topic topic, boolean hasNotReadAttachment){
        List<List<TextButton>> buttons = new ArrayList<>();

        TextButton editButton = new TextButton( new Action ("Редактировать", Map.of("topic_id", topic.getId(),
                "neededAction", bot.enums.Action.START_EDIT_TOPIC)) );
        TextButton deleteButton = new TextButton( new Action ("Удалить", Map.of("topic_id", topic.getId(),
                "neededAction", bot.enums.Action.START_DELETE_TOPIC)) );
        TextButton showButton = new TextButton( new Action ("Просмотреть", Map.of("topic_id", topic.getId(),
                "neededAction", bot.enums.Action.SHOW_ATTACHMENTS)) );

        if(topic.getName().equals(TopicRepository.withoutName))
            buttons.add(List.of(deleteButton));
        else
            buttons.add(List.of(editButton, deleteButton));

        if(hasNotReadAttachment)
            buttons.add(List.of(showButton));

        return new Keyboard(buttons);
    }

    public  static Keyboard ofTopics(List<Topic> topics){
        bot.enums.Action neededAction = bot.enums.Action.SET_ATTACHMENT_TOPIC_BY_KEYBOARD;
        List<List<TextButton>> buttons = new ArrayList<>();

        if (topics != null) {
            List<TextButton> buffer = new ArrayList<>();
            Topic current;
            int size = topics.size();
            for (int i = 0; i < size; i++) {
                current = topics.get(i);

                buffer.add(new TextButton( new Action (current.getName(), Map.of("topic_id" , current.getId(),
                        "neededAction", neededAction.name()))));

                if ((i != 0 && (i + 1) % DEFAULT_COUNT_BUTTON_ON_LINE == 0) || i == size - 1) {
                    buttons.add(buffer);
                    buffer = new ArrayList<>();
                }
            }
        }
        return new Keyboard(buttons);
    }

}
