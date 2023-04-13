package sauprojects.sauvest.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sauprojects.sauvest.dto.UserDTO;

public interface AuthenticationService {
	
	void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	UserDTO signUp(UserDTO userDTO);

	void verifyAccount(String token);

	Boolean checkToken(String token);

	Boolean checkEnabled(String username);
}
