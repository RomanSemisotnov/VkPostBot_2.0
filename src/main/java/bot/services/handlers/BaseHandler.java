package bot.services.handlers;

import bot.entities.MessageBody;
import bot.entities.User;
import bot.enums.Action;
import bot.repositories.AttachmentRepository;
import bot.repositories.ReminderRepository;
import bot.repositories.TopicRepository;
import bot.repositories.UserRepository;
import bot.services.vkClient.VkMessageSenderService;
import bot.storages.LastIncomingAttachmentStorage;
import bot.storages.TopicStorage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseHandler{

    @Autowired
    protected ReminderRepository reminderRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected TopicRepository topicRepository;

    @Autowired
    protected AttachmentRepository attachmentRepository;

    @Autowired
    protected VkMessageSenderService vkSenderService;

    @Autowired
    protected LastIncomingAttachmentStorage lastIncomingAttachmentStorage;

    @Autowired
    protected TopicStorage topicStorage;

    @Getter
    @Setter
    private Action processingAction;

    abstract public boolean handle(MessageBody body, User user);

}
