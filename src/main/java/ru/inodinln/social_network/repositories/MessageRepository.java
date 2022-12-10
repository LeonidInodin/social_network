package ru.inodinln.social_network.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.inodinln.social_network.entities.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdIs(Long userId); //Get all sended messages by user;

    List<Message> findByRecipientIdIs(Long userId); //Get all recieved messages by user;
}
