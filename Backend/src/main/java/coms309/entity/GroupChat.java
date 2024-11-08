package coms309.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "group_chat")
public class GroupChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_chat_id")
    private Long groupChatId;

    private String groupChatName;

    @ManyToMany
    @JoinTable(name = "group_chat_user",
            joinColumns = @JoinColumn(name = "group_chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<UserProfile> users;

    @OneToMany(mappedBy = "groupChat")
    private List<Message> messages;


    public GroupChat() {
    }

    public GroupChat(String groupChatName) {
        this.groupChatName = groupChatName;
    }


}
