package ru.inodinln.social_network.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.inodinln.social_network.entities.Media;
import ru.inodinln.social_network.entities.Message;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {

}
