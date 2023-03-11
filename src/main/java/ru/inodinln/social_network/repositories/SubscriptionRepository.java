package ru.inodinln.social_network.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.inodinln.social_network.entities.Subscription;
import ru.inodinln.social_network.entities.User;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    //Get all subscriptions by current user:
    List<Subscription> findBySubscriber(User user, Pageable pageable);

    List<Subscription> findBySubscriber(User user);

    //Get all subscriptions to current user:
    List<Subscription> findByTarget(User user, Pageable pageable);

    //Check is exists new subscription
    Subscription findBySubscriberAndTarget(User subscriber, User target);
}
