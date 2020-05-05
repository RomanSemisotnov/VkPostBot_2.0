package bot.services.vkClient;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VkUserInfoService extends BaseVkClient{


    public UserXtrCounters get(Integer vkUserID){
        List<String> ids = List.of(String.valueOf(vkUserID));
        List<UserXtrCounters> users = null;
        try {
            users = vkApiClient.users().get(myGroupActor).userIds(ids).execute();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return users != null ? users.get(0) : null;
    }


}
