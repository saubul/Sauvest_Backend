package sauprojects.sauvest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sauprojects.sauvest.entity.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long>{

	List<Subscription> findAllByUserId(Long userId);
	
	Optional<Subscription> findByUserIdAndSubscriptionUserId(Long userId, Long subscriptionUserId);
	
	void deleteByUserIdAndSubscriptionUserId(Long userId, Long subscriptionUserId);
	
}
