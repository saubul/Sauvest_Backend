package sauprojects.sauvest.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sauprojects.sauvest.entity.Chat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatDTO {
	
	private Long id;
	private List<String> usersUsername;
	private String name;
	
	public static ChatDTO buildChatDTO(Chat chat) {
		List<String> usersUsername = chat.getUsers().stream().map(user -> user.getUsername()).collect(Collectors.toList());
		
		return ChatDTO.builder()
						  .id(chat.getId())
						  .usersUsername(usersUsername)
						  .name(chat.getName())
					  .build();
	}

}