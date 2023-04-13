package sauprojects.sauvest.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import sauprojects.sauvest.dto.UserDTO;
import sauprojects.sauvest.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	
	@GetMapping("/refreshToken")
	public HttpEntity<Void> refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
		authenticationService.refreshToken(request, response);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PostMapping("/signUp")
	public HttpEntity<UserDTO> signUp(@RequestBody UserDTO userDTO) {
		return new ResponseEntity<UserDTO>(authenticationService.signUp(userDTO), HttpStatus.CREATED);
	}
	
	@GetMapping("/accountVerification/{token}")
	public HttpEntity<Void> verifyAccount(@PathVariable("token") String token) {
		
		authenticationService.verifyAccount(token);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("/checkToken")
	public HttpEntity<Boolean> checkToken(@RequestParam("token") String token) {
		return new ResponseEntity<Boolean>(authenticationService.checkToken(token), HttpStatus.OK);
	}
	
	@GetMapping("/checkEnabled")
	public HttpEntity<Boolean> checkEnabled(@RequestParam("username") String username) {
		return new ResponseEntity<Boolean>(authenticationService.checkEnabled(username), HttpStatus.OK);
	}
}
