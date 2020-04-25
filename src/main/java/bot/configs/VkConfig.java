package bot.configs;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value = "classpath:vk.properties")
@Getter
public class VkConfig {

    private final Environment environment;
    private final String secret;
    private final String access_token;

    public VkConfig(Environment environment) {
        this.environment = environment;
        secret = environment.getProperty("vk.secret");
        access_token = environment.getProperty("vk.access_token");
    }

    @Bean
    public VkApiClient vkApiClient() {
        return new VkApiClient(HttpTransportClient.getInstance());
    }

    @Bean
    public GroupActor myGroupActor() {
        return new GroupActor(Integer.parseInt(environment.getProperty("vk.myGroupId")), environment.getProperty("vk.access_token"));
    }

}
