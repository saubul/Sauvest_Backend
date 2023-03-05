package sauprojects.sauvest.service;

import java.util.List;

import sauprojects.sauvest.dto.ChatDTO;
import sauprojects.sauvest.dto.UserDTO;
import sauprojects.sauvest.entity.Chat;

public interface ChatService {
	
	Chat findChatById(Long chatId);
	
	List<ChatDTO> findAllChatsByUserId(Long userId);
	
	List<ChatDTO> findAllChatsByUsername(String username);
	
	UserDTO addUserToChat(Long chatId, Long userId);
	
	UserDTO addUserToChat(Long chatId, String username);
	
	ChatDTO createChat(ChatDTO chatDTO);
	
	Chat buildChat(ChatDTO chatDTO);
	
	Chat findChatByName(String name);
	
}
