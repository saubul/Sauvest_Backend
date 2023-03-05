package sauprojects.sauvest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sauprojects.sauvest.entity.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDTO {
	
	private String username;
	private String email;
	private String name;
	private String surname;

	public static ProfileDTO buildProfileDTO(User user) {
		return ProfileDTO.builder()
						  .username(user.getUsername())
						  .email(user.getEmail())
						  .name(user.getName())
						  .surname(user.getSurname())
					  .build();
	}
}
