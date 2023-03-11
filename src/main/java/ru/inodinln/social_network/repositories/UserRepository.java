package ru.inodinln.social_network.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.inodinln.social_network.entities.Conversation;
import ru.inodinln.social_network.entities.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findUsersByConversationsContains(Conversation conversation, Pageable pageable);

    Optional<User> findByEmail(String eMail);

    @Query(value = "SELECT a.* FROM users AS a INNER JOIN " +
            "(SELECT author_id, COUNT(*) AS posts_count FROM post WHERE timestamp " +
            "BETWEEN :startOfPeriod AND :endOfPeriod GROUP BY author_id) AS b ON a.id = b.author_id " +
            "ORDER BY b.posts_count DESC"
            , nativeQuery = true)
    List<User> getMostActiveUsers(@Param("startOfPeriod") LocalDateTime startOfPeriod,
                                            @Param("endOfPeriod") LocalDateTime endOfPeriod, Pageable pageable);


}
