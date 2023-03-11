package ru.inodinln.social_network.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.inodinln.social_network.entities.Post;
import ru.inodinln.social_network.entities.User;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findPostsByAuthor(User author, Pageable pageable);


    ////////////////////////////Statistics methods section///////////////////////////////////////

    Long countPostsByTimestampBetween(LocalDateTime startOfPeriod, LocalDateTime endOfPeriod);

    Long countPostsByAuthorAndTimestampBetween(User user, LocalDateTime startOfPeriod, LocalDateTime endOfPeriod);

    List<Post> findPostsByTimestampBetween(LocalDateTime startOfPeriod, LocalDateTime endOfPeriod, Sort criteria);

   @Query(value = "SELECT a.* FROM post as a INNER JOIN subscription as b ON a.author_id = (SELECT b.target_id WHERE subscriber_id = :userId)"
           , nativeQuery = true)
    List<Post> getPostsByTarget(@Param("userId") Long userId, Pageable pageable);


}
