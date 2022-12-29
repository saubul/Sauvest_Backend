package sauprojects.sauvest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import sauprojects.sauvest.entity.Subscription;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionDTO {
	
	private Long userId;
	private Long subscriptionUserId;
	
	public static SubscriptionDTO buildSubscriptionDTO(Subscription subscription) {
		return SubscriptionDTO.builder()
								  .userId(subscription.getUser().getId())
								  .subscriptionUserId(subscription.getSubscriptionUser().getId())
							  .build();
	}
	
}
