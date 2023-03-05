package sauprojects.sauvest.service;

import java.util.List;

import sauprojects.sauvest.dto.ProfileDTO;
import sauprojects.sauvest.dto.SubscriptionDTO;
import sauprojects.sauvest.entity.Subscription;

public interface SubscriptionService {
	
	SubscriptionDTO saveSubscription(SubscriptionDTO subscriptionDTO);
	
	SubscriptionDTO saveSubscription(Subscription subscription);
	
	Subscription buildSubscription(SubscriptionDTO subscriptionDTO);
	
	List<SubscriptionDTO> getSubscriptionsByUsername(String username);

	Boolean checkIsSubscribed(SubscriptionDTO subscriptionDTO);

	SubscriptionDTO deleteSubscription(SubscriptionDTO subscriptionDTO);

	List<ProfileDTO> getAllUsersSubscriptionsByUsername(String username);
	
}
