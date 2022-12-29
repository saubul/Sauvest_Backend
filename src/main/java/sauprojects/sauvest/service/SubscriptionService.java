package sauprojects.sauvest.service;

import sauprojects.sauvest.dto.SubscriptionDTO;
import sauprojects.sauvest.entity.Subscription;

public interface SubscriptionService {
	
	SubscriptionDTO saveSubscription(SubscriptionDTO subscriptionDTO);
	
	SubscriptionDTO saveSubscription(Subscription subscription);
	
	Subscription buildSubscription(SubscriptionDTO subscriptionDTO);
	
}
