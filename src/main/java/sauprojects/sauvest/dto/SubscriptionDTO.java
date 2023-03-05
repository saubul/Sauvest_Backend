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
	
	private String username;
	private String subUsername;
	
	public static SubscriptionDTO buildSubscriptionDTO(Subscription subscription) {
		return SubscriptionDTO.builder()
								  .username(subscription.getUser().getUsername())
								  .subUsername(subscription.getSubscriptionUser().getUsername())
							  .build();
	}
	
}
