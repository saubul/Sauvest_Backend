package sauprojects.sauvest.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sauprojects.sauvest.dto.ChatMessageDTO;
import sauprojects.sauvest.entity.ChatMessage;
import sauprojects.sauvest.repository.ChatMessageRepository;
import sauprojects.sauvest.service.ChatMessageService;
import sauprojects.sauvest.service.ChatService;
import sauprojects.sauvest.service.UserService;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService{
	
	private final ChatMessageRepository chatMessageRepository;
	private final UserService userService;
	private final ChatService chatService;
	
	@Override
	public List<ChatMessageDTO> findAllChatMessagesByChatId(Long chatId) {
		return chatMessageRepository.findAllByChatId(chatId).stream().map(ChatMessageDTO::buildChatDTO).collect(Collectors.toList());
	}

	@Override
	public ChatMessageDTO saveChatMessage(ChatMessage chatMessage) {
		return ChatMessageDTO.buildChatDTO(chatMessageRepository.save(chatMessage));
	}

	@Override
	public ChatMessageDTO saveChatMessage(ChatMessageDTO chatMessageDTO) {
		ChatMessage chatMessage = this.buildChatMessage(chatMessageDTO);
		chatMessageRepository.save(chatMessage);
		return chatMessageDTO;
	}
	
	@Override
	public ChatMessage buildChatMessage(ChatMessageDTO chatMessageDTO) {
		return ChatMessage.builder()
							  .content(chatMessageDTO.getContent())
							  .user(userService.findUserByUsername(chatMessageDTO.getUsername()))
							  .chat(chatService.findChatById(chatMessageDTO.getChatId()))
					  	  .build();
	}
}
