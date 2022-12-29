package sauprojects.sauvest.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sauprojects.sauvest.dto.SubscriptionDTO;
import sauprojects.sauvest.entity.Subscription;
import sauprojects.sauvest.repository.SubscriptionRepository;
import sauprojects.sauvest.repository.UserRepository;
import sauprojects.sauvest.service.SubscriptionService;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService{
	
	private final SubscriptionRepository subscriptionRepository;
	private final UserRepository userRepository;

	@Override
	public SubscriptionDTO saveSubscription(SubscriptionDTO subscriptionDTO) {
		subscriptionRepository.save(this.buildSubscription(subscriptionDTO));
		return subscriptionDTO;
	}

	@Override
	public SubscriptionDTO saveSubscription(Subscription subscription) {
		return SubscriptionDTO.buildSubscriptionDTO(subscriptionRepository.save(subscription));
	}

	@Override
	public Subscription buildSubscription(SubscriptionDTO subscriptionDTO) {
		return Subscription.builder()
							   .user(userRepository.findById(subscriptionDTO.getUserId())
									   			 						.orElseThrow(() -> 
									   			 										new RuntimeException(String.format("User with id '%s' not found", subscriptionDTO.getUserId()))))
							   .subscriptionUser(userRepository.findById(subscriptionDTO.getSubscriptionUserId())
									   						   						.orElseThrow(() -> 
									   						   						new RuntimeException(String.format("User with id '%s' not found", subscriptionDTO.getSubscriptionUserId()))))
						   .build();
	}
	
	
	
}
