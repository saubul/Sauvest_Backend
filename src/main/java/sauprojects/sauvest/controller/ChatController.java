package sauprojects.sauvest.controller;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import sauprojects.sauvest.dto.ChatDTO;
import sauprojects.sauvest.dto.ChatMessageDTO;
import sauprojects.sauvest.service.ChatMessageService;
import sauprojects.sauvest.service.ChatService;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
	
	private final ChatService chatService;
	private final ChatMessageService chatMessageService;
	
	@PostMapping
	public HttpEntity<ChatDTO> createChat(@RequestBody ChatDTO chatDTO) {
		return new ResponseEntity<ChatDTO>(chatService.createChat(chatDTO), HttpStatus.OK);
	}
	
	@GetMapping("/{chatId}/messages")
	public ResponseEntity<List<ChatMessageDTO>> getChatMessages(@PathVariable("chatId") Long chatId) {
		return new ResponseEntity<List<ChatMessageDTO>>(chatMessageService.findAllChatMessagesByChatId(chatId), HttpStatus.OK);
	}
	
}
