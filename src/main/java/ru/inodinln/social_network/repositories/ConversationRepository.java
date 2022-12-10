package ru.inodinln.social_network.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.inodinln.social_network.entities.Conversation;
import ru.inodinln.social_network.entities.User;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    List<Conversation> findConversationsByMembersContaining(User user, Pageable pageable);

}
