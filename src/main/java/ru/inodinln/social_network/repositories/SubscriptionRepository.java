package ru.inodinln.social_network.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.inodinln.social_network.entities.Subscription;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    //Get all subscriptions by current user:
    List<Subscription> findByFrom_IdIs(Long userId);

    //Get all subscriptions to current user:
    List<Subscription> findByToIdIs(Long userId);

    //Check is exists new subscription
    Subscription findByFrom_IdAndToId(Long fromId, Long toId);
}
