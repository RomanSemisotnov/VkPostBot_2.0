package bot.repositories;

import bot.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic,Integer> {

    String withoutName = "Без темы";

    @Modifying
    @Query("UPDATE Topic topic SET topic.name = :new_name WHERE topic.id = :topic_id")
    void updateNameById(@Param("topic_id") Integer topicId, @Param("new_name") String newTopicName);

    List<Topic> findByUserIdOrderByName(Integer userId);

    Topic findByUserIdAndName(Integer userId, String name);

    default Topic getWithoutName(Integer userId){
        Topic topic = findByUserIdAndName(userId, withoutName);

        if(topic != null)
            return topic;

        return save(Topic.builder()
                .name(withoutName)
                .userId(userId)
                .build()
        );
    }

}
