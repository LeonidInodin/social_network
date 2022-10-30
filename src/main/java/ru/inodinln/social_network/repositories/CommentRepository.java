package ru.inodinln.social_network.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.inodinln.social_network.entities.Comment;


public interface CommentRepository extends JpaRepository<Comment, Long> {

}
