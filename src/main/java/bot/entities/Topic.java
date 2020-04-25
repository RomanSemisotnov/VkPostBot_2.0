package bot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "topics")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "user_id")
    @EqualsAndHashCode.Include
    private int userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @JsonIgnore
    @ToString.Exclude
    private User user;

    @OneToMany( cascade = CascadeType.DETACH, mappedBy = "topic", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Attachment> attachments;

}
