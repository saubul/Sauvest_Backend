package sauprojects.sauvest.controller;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sauprojects.sauvest.dto.ProfileDTO;
import sauprojects.sauvest.dto.SubscriptionDTO;
import sauprojects.sauvest.service.SubscriptionService;

@RestController
@RequestMapping("/api/subscription")
@RequiredArgsConstructor
public class SubscriptionController {
	
	private final SubscriptionService subscriptionService;
	
	@PostMapping("/subscribe")
	public HttpEntity<SubscriptionDTO> subscribe(@RequestBody SubscriptionDTO subscriptionDTO) {
		return new ResponseEntity<SubscriptionDTO>(subscriptionService.saveSubscription(subscriptionDTO), HttpStatus.OK);
	}
	
	@PostMapping("/unsubscribe")
	public HttpEntity<SubscriptionDTO> unsubscribe(@RequestBody SubscriptionDTO subscriptionDTO) {
		return new ResponseEntity<SubscriptionDTO>(subscriptionService.deleteSubscription(subscriptionDTO), HttpStatus.OK);
	}
	
	@GetMapping("/isSubscribed")
	public HttpEntity<Boolean> isSubscribed(SubscriptionDTO subscriptionDTO) {
		return new ResponseEntity<Boolean>(subscriptionService.checkIsSubscribed(subscriptionDTO), HttpStatus.OK);
	}
	
	@GetMapping("/getAllByUsername")
	public HttpEntity<List<ProfileDTO>> getAllSubscriptionsByUsername(@RequestParam("username") String username) {
		return new ResponseEntity<List<ProfileDTO>>(subscriptionService.getAllUsersSubscriptionsByUsername(username), HttpStatus.OK);
	}

}
