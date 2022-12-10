package ru.inodinln.social_network.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.inodinln.social_network.entities.Post;
import ru.inodinln.social_network.entities.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<List<Post>> findPostsByAuthor(User author);

    Optional<List<Post>> findPostsByAuthor(User author, Pageable pageable);

    Optional<List<Post>> findPostsByAuthorIn(List<User> users, Pageable pageable);

    ////////////////////////////Statistics methods section///////////////////////////////////////

    Long findCountPostsByTimestampBetween(LocalDateTime startOfPeriod, LocalDateTime endOfPeriod);

    Optional<List<Post>> findPostsByTimestampBetween(LocalDateTime startOfPeriod, LocalDateTime endOfPeriod);

    Optional<List<Post>> findPostsByTimestampBetween(LocalDateTime startOfPeriod, LocalDateTime endOfPeriod, Sort criteria);


}
