package sauprojects.sauvest.controller;


import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sauprojects.sauvest.dto.ChatDTO;
import sauprojects.sauvest.dto.ProfileDTO;
import sauprojects.sauvest.dto.UserDTO;
import sauprojects.sauvest.service.ChatService;
import sauprojects.sauvest.service.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	private final ChatService chatService;
	
	@GetMapping("/getUser")
	public HttpEntity<UserDTO> getUser(@RequestParam("username") String username) {
		return new ResponseEntity<UserDTO>(UserDTO.buildUserDTO(userService.findUserByUsername(username)), HttpStatus.OK);
	}
	
	@GetMapping("/getUsersByUsernameContaining")
	public HttpEntity<List<ProfileDTO>> getUsersByUsernameContaining(@RequestParam("string") String string) {
		return new ResponseEntity<List<ProfileDTO>>(userService.getAllUsersBySurnameStartsWith(string), HttpStatus.OK);
	}
	
	@GetMapping("/{username}/chats")
	public ResponseEntity<List<ChatDTO>> getChatsByUser(@PathVariable("username") String username) {
		
		return new ResponseEntity<List<ChatDTO>>(chatService.findAllChatsByUsername(username), HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {

		return new ResponseEntity<>(userService.updateUser(userDTO), HttpStatus.OK);
	}
	
}
