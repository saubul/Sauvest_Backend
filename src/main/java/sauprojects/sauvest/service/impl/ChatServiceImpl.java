package sauprojects.sauvest.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sauprojects.sauvest.dto.ChatDTO;
import sauprojects.sauvest.dto.UserDTO;
import sauprojects.sauvest.entity.Chat;
import sauprojects.sauvest.entity.User;
import sauprojects.sauvest.repository.ChatRepository;
import sauprojects.sauvest.service.ChatService;
import sauprojects.sauvest.service.UserService;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{
	
	private final ChatRepository chatRepository;
	private final UserService userService;

	@Override
	public Chat findChatById(Long chatId) {
		Optional<Chat> chatOptional = chatRepository.findById(chatId);
		
		Chat chat = chatOptional.orElseThrow(() -> new RuntimeException(String.format("Chat with ID '%s' not found.", chatId)));
		
		return chat;
	}

	@Override
	public Chat findChatByName(String name) {
		Optional<Chat> chatOptional = chatRepository.findByName(name);
		
		Chat chat = chatOptional.orElseThrow(() -> new RuntimeException(String.format("Chat with Name '%s' not found.", name)));
		
		return chat;
	}
	
	@Override
	public List<ChatDTO> findAllChatsByUserId(Long userId) {
		return chatRepository.findByUserId(userId).stream().map(ChatDTO::buildChatDTO).collect(Collectors.toList());
	}
	
	@Override
	public List<ChatDTO> findAllChatsByUsername(String username) {
		User user = userService.findUserByUsername(username);
		return chatRepository.findByUserId(user.getId()).stream().map(ChatDTO::buildChatDTO).collect(Collectors.toList());
	}

	@Override
	public UserDTO addUserToChat(Long chatId, Long userId) {
		User user = userService.findUserById(userId);
		Chat chat = this.findChatById(chatId);
		
		chat.getUsers().add(user);
		
		chatRepository.save(chat);
		
		return UserDTO.buildUserDTO(user);
	}

	@Override
	public UserDTO addUserToChat(Long chatId, String username) {
		User user = userService.findUserByUsername(username);
		Chat chat = this.findChatById(chatId);
		
		chat.getUsers().add(user);
		
		chatRepository.save(chat);
		
		return UserDTO.buildUserDTO(user);
	}

	@Override
	public ChatDTO createChat(ChatDTO chatDTO) {
		try { //refactor
			this.findChatByName(chatDTO.getName());
		} catch (Exception e) {
			chatRepository.save(this.buildChat(chatDTO));
		}
		return chatDTO;
	}

	@Override
	public Chat buildChat(ChatDTO chatDTO) {
		return Chat.builder()
					   .name(chatDTO.getName())
					   .users(chatDTO.getUsersUsername().stream().map(username -> userService.findUserByUsername(username)).toList())
				   .build();
	}

}
