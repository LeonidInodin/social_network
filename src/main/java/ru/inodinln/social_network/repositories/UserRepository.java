package ru.inodinln.social_network.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.inodinln.social_network.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
