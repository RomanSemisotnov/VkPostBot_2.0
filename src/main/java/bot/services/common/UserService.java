package bot.services.common;

import bot.entities.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public String getTurn(User user){
        if(user.isCustomTurnFlag())
            return user.getCustomTurn();
        else
            return user.getDefaultTurn() + " " + user.getName();
    }

}
