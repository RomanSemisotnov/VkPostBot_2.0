package bot.repositories;

import bot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {


    User findByVkId(int vkId);

    default User findOrCreate(int vkId){
        User user = findByVkId(vkId);

        if(user != null)
            return user;

        return save(User.builder().vkId(vkId).build());
    }

    @Modifying
    @Query("UPDATE User user SET user.lastMessageSec = :last_message_sec WHERE user.id = :user_ud")
    void updateLastDateById(@Param("user_ud") Integer userId, @Param("last_message_sec") long lastMessageSec);

}
