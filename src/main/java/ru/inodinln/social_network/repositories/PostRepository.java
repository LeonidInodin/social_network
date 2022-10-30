package ru.inodinln.social_network.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.inodinln.social_network.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
