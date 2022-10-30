package ru.inodinln.social_network.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.inodinln.social_network.entities.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {

}
