package ru.inodinln.social_network.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.inodinln.social_network.entities.Comment;
import ru.inodinln.social_network.entities.Post;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findCommentsByPostAndLevel(Post post, Integer level, Pageable pageable);

    List<Comment> findCommentsByPost(Post post, Pageable pageable);
}
