package bot.entities;

import bot.deserializers.AttachmentCustomDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attachments")
@JsonDeserialize(using = AttachmentCustomDeserializer.class)
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "vk_identifier")
    private int vkIdentifier; // id is json

    @Column(name = "owner_id")
    private int ownerId; // from_id in json

    @Column(name = "type")
    private String type;

    @Column(name = "topic_id")
    private Integer topicId;

    @Column(name = "isRead")
    private boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", insertable = false, updatable = false)
    @JsonIgnore
    @ToString.Exclude
    private Topic topic;

}
