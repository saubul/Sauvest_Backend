package sauprojects.sauvest.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import sauprojects.sauvest.dto.ProfileDTO;
import sauprojects.sauvest.dto.SubscriptionDTO;
import sauprojects.sauvest.entity.Subscription;
import sauprojects.sauvest.repository.SubscriptionRepository;
import sauprojects.sauvest.repository.UserRepository;
import sauprojects.sauvest.service.SubscriptionService;
import sauprojects.sauvest.service.UserService;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService{
	
	private final SubscriptionRepository subscriptionRepository;
	private final UserRepository userRepository;
	private final UserService userService;

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
	public List<SubscriptionDTO> getSubscriptionsByUsername(String username) {
		return subscriptionRepository.findAllByUserId(userService.findUserByUsername(username).getId()).stream().map(SubscriptionDTO::buildSubscriptionDTO).toList();
	}

	@Override
	@Transactional
	public SubscriptionDTO deleteSubscription(SubscriptionDTO subscriptionDTO) {
		subscriptionRepository.deleteByUserIdAndSubscriptionUserId(userService.findUserByUsername(subscriptionDTO.getUsername()).getId(), 
				 userService.findUserByUsername(subscriptionDTO.getSubUsername()).getId());
		return subscriptionDTO;
	}

	@Override
	public Boolean checkIsSubscribed(SubscriptionDTO subscriptionDTO) {
		Optional<Subscription> subscriptionOptional = subscriptionRepository
														  .findByUserIdAndSubscriptionUserId(userService.findUserByUsername(subscriptionDTO.getUsername()).getId(), 
																  							 userService.findUserByUsername(subscriptionDTO.getSubUsername()).getId());
		if(subscriptionOptional.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<ProfileDTO> getAllUsersSubscriptionsByUsername(String username) {
		List<SubscriptionDTO> subscriptionDTOs = this.getSubscriptionsByUsername(username);
		List<ProfileDTO> profileDTOs = subscriptionDTOs.stream().map(subscriptionDTO -> {
			ProfileDTO profileDTO = ProfileDTO.buildProfileDTO(userService.findUserByUsername(subscriptionDTO.getSubUsername()));
			return profileDTO;
		}).sorted((o1, o2) -> o1.getSurname().compareTo(o2.getSurname())).toList();
		return profileDTOs;
	}

	@Override
	public Subscription buildSubscription(SubscriptionDTO subscriptionDTO) {
		return Subscription.builder()
							   .user(userRepository.findByUsername(subscriptionDTO.getUsername())
									   			 						.orElseThrow(() -> 
									   			 										new RuntimeException(String.format("User with id '%s' not found", subscriptionDTO.getUsername()))))
							   .subscriptionUser(userRepository.findByUsername(subscriptionDTO.getSubUsername())
									   						   						.orElseThrow(() -> 
									   						   						new RuntimeException(String.format("User with id '%s' not found", subscriptionDTO.getSubUsername()))))
						   .build();
	}
	
}
