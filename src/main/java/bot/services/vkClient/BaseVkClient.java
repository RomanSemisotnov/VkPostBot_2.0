package bot.services.vkClient;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseVkClient {

    @Autowired
    public VkApiClient vkApiClient;

    @Autowired
    public GroupActor myGroupActor;

}
