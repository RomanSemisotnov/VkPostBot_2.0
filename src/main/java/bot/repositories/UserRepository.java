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

    @Modifying
    @Query("UPDATE User user SET user.lastMessageSec = :last_message_sec WHERE user.id = :user_id")
    void updateLastDateMessage(@Param("user_id") Integer userId, @Param("last_message_sec") long lastMessageSec);

    @Modifying
    @Query("UPDATE User user SET user.nickName = :nick_name WHERE user.id = :user_id")
    void updateNickName(@Param("user_id") Integer userId, @Param("nick_name") String profession);

    @Modifying
    @Query("UPDATE User user SET user.profession = :profession WHERE user.id = :user_id")
    void updateProfession(@Param("user_id") Integer userId, @Param("profession") String profession);

    @Modifying
    @Query("UPDATE User user SET user.frequencyRemember = :frequency_remember WHERE user.id = :user_id")
    void updateFrequencyRemember(@Param("user_id") Integer userId, @Param("frequency_remember") String frequencyRemember);

    @Modifying
    @Query("UPDATE User user SET user.notReadCount = :not_read_count WHERE user.id = :user_id")
    void updateNotReadCount(@Param("user_id") Integer userId, @Param("not_read_count") String notReadCount);

}
