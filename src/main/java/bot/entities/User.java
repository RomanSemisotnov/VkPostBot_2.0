package bot.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "vk_id")
    private int vkId;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Topic> topics;

    @Column(name = "last_message")
    private Long lastMessageSec;

}
