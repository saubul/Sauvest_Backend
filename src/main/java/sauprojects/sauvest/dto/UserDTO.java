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
public class UserDTO {
	
	private String username;
	private String password;
	private String email;
	private String name;
	private String surname;
	public boolean enabled;
	private String imgPath;

	public static UserDTO buildUserDTO(User user) {
		return UserDTO.builder()
						  .username(user.getUsername())
						  .password(user.getPassword())
						  .email(user.getEmail())
						  .name(user.getName())
						  .surname(user.getSurname())
						  .enabled(user.isEnabled())
						  .imgPath(user.getImgPath())
					  .build();
	}
	
}
