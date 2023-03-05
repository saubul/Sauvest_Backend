package sauprojects.sauvest.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sauprojects.sauvest.entity.ChatMessage;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5836955011551164933L;
	private String username;
	private Long chatId;
	private String content;
	
	public static ChatMessageDTO buildChatDTO(ChatMessage chatMessage) {
		return ChatMessageDTO.builder()
								 .username(chatMessage.getUser().getUsername())
								 .chatId(chatMessage.getChat().getId())
								 .content(chatMessage.getContent())
							 .build();
	}
	
}
