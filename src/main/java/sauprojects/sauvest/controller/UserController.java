package sauprojects.sauvest.controller;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sauprojects.sauvest.dto.UserDTO;
import sauprojects.sauvest.service.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/getUser")
	public HttpEntity<UserDTO> getUser(@RequestParam("username") String username) {
		return new ResponseEntity<UserDTO>(UserDTO.buildUserDTO(userService.findUserByUsername(username)), HttpStatus.OK);
	}
	
	@PostMapping("/saveImg")
	public HttpEntity<String> saveUserImg(@RequestParam("username") String username,
										  @RequestParam("imagename") String imageName) {
		return new ResponseEntity<String>(userService.saveUserImg(username, imageName), HttpStatus.OK);
	}
}
