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

    @Column(name = "name")
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Topic> topics;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Reminder> reminders;

    @Column(name = "last_message")
    private Long lastMessageSec;

    @Column(name = "default_turn")
    private String defaultTurn;

    @Column(name = "is_custom_turn")
    private boolean isCustomTurnFlag;

    @Column(name = "custom_turn")
    private String customTurn;

    @ToString.Exclude
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private UserStats stats;

}
