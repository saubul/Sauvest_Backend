package sauprojects.sauvest.service;

import java.util.List;

import sauprojects.sauvest.dto.ChatMessageDTO;
import sauprojects.sauvest.entity.ChatMessage;

public interface ChatMessageService {

	List<ChatMessageDTO> findAllChatMessagesByChatId(Long chatId);
	
	ChatMessageDTO saveChatMessage(ChatMessage chatMessage);

	ChatMessageDTO saveChatMessage(ChatMessageDTO chatMessageDTO);
	
	ChatMessage buildChatMessage(ChatMessageDTO chatMessageDTO);
}
