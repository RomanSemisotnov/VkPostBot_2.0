package bot.repositories;

import bot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
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

}
