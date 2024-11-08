package coms309.repository;

import coms309.entity.GroupChat;
import coms309.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
        List<Message> findAllByGroupChat(GroupChat groupChat);
    }

