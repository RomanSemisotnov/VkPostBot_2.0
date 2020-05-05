package bot.repositories;

import bot.entities.Attachment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment,Integer> {

    List<Attachment> findByTopicIdAndIsRead(Integer topicId, boolean isRead);

    default Attachment findSameAttachmentAtUser(Integer userId, Integer vkIdentifier){
        List<Attachment> attachments = findSameAttachmentAtUser(userId, vkIdentifier, PageRequest.of(0, 1)).getContent();

        if(attachments.isEmpty())
            return null;

        return attachments.get(0);
    }

    @Query( value =
    "SELECT att FROM Attachment att " +
            "WHERE att.topic.userId = :user_id " +
            "AND att.vkIdentifier = :vk_identifier " +
            "AND att.isRead = FALSE")
    Page<Attachment> findSameAttachmentAtUser(
            @Param("user_id") Integer userId,
            @Param("vk_identifier") Integer vkIdentifier,
            Pageable pageable
    );

    default Attachment findWithSameName(Integer userId, String name){
        List<Attachment> attachments = findByUserIdAndName(userId, name, PageRequest.of(0, 1)).getContent();

        if(attachments.isEmpty())
            return null;

        return attachments.get(0);
    }

    @Query( value =
            "SELECT att FROM Attachment att " +
                    "WHERE att.topic.userId = :user_id " +
                    "AND att.name = :name ")
    Page<Attachment> findByUserIdAndName(
            @Param("user_id") Integer userId,
            @Param("name") String name,
            Pageable pageable
    );

    @Modifying
    @Query("UPDATE Attachment att SET att.isRead = :is_read WHERE att.id = :attach_id")
    void updateRead(@Param("attach_id") Integer attachId, @Param("is_read") Boolean isRead);

    @Modifying
    @Query("UPDATE Attachment att SET att.topicId = :topic_id WHERE att.id IN (:attach_ids)")
    void updateAttachmentsTopic(@Param("attach_ids") List<Integer> attachId, @Param("topic_id") Integer topicId);

}
