package sauprojects.sauvest.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import sauprojects.sauvest.dto.ChatMessageDTO;
import sauprojects.sauvest.service.ChatMessageService;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {
	
	private final ChatMessageService chatMessageService;
	private final SimpMessagingTemplate simpMessagingTemplate;
	
	@MessageMapping("/chat/{chatId}")
	public void sendChatMessage(@DestinationVariable Long chatId, ChatMessageDTO chatMessageDTO) {
		chatMessageService.saveChatMessage(chatMessageDTO);
		simpMessagingTemplate.convertAndSend("/topic/" + chatId, chatMessageDTO);
		
	}
	
}
