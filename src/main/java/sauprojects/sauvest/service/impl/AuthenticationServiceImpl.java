package sauprojects.sauvest.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import sauprojects.sauvest.dto.UserDTO;
import sauprojects.sauvest.entity.NotificationEmail;
import sauprojects.sauvest.entity.User;
import sauprojects.sauvest.entity.VerificationToken;
import sauprojects.sauvest.service.AuthenticationService;
import sauprojects.sauvest.service.MailService;
import sauprojects.sauvest.service.UserService;
import sauprojects.sauvest.service.VerificationTokenService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
	
	private final UserService userService;
	private final VerificationTokenService verificationTokenService;
	private final MailService mailService;
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expirationTime}")
	private String jwtExpirationTime;
	
	@Value("${mail_server}")
	private String mail_server;
	
	@Override
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		System.out.println(authorizationHeader);
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				String refreshToken = authorizationHeader.substring("Bearer ".length());
				
				Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
				
				JWTVerifier verifier = JWT.require(algorithm).build();
				
				DecodedJWT decodedJWT = verifier.verify(refreshToken);
				
				String username = decodedJWT.getClaim("username").asString();
				
				User user = userService.findUserByUsername(username);
				Date accessTokenExpiresAtDate = new Date(System.currentTimeMillis() + Long.parseLong(jwtExpirationTime));
				
				String accessToken = JWT.create()
						.withExpiresAt(accessTokenExpiresAtDate)
						.withClaim("username", user.getUsername())
						.withClaim("roles", user.getRoles().stream().map(role -> role.getRoleName().name()).collect(Collectors.toList()))
					.sign(algorithm);
				
				
				Map<String, String> responseMap = new HashMap<String, String>();
				
				responseMap.put("accessToken", accessToken);
				
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
				
			} catch (Exception e) {
				response.sendError(401, e.getMessage());
			}
		} else {
			response.sendError(401, "Wrong authorization header (trying to refresh token)");
		}
	}

	@Override
	public UserDTO signUp(UserDTO userDTO) {
		User user = userService.buildUser(userDTO);
		
		userService.saveUser(user);
		
		String verificationToken = verificationTokenService.generateVerificationToken(user);
		
		mailService.sendMail(NotificationEmail.builder()
											  	  .recipient(user.getEmail())
											  	  .subject("SAUSOCIAL ACCOUNT ACTIVATION")
											  	  .body("Thank you for signing up to Sauvest, " +
											            "please click on the link below to activate your account : " +
											            "http://" + mail_server + "/successActivate/" + verificationToken)
											  .build());
		
		return userDTO;
	}
	
	@Override
	@Transactional
	public void verifyAccount(String token) {
		VerificationToken verificationToken = verificationTokenService.findByToken(token);
		
		User user = userService.findUserByUsername(verificationToken.getUser().getUsername());
		
		user.setEnabled(true);
		
		userService.saveUser(user);
	}

	@Override
	public Boolean checkToken(String token) {
		Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
		
		try {
			JWT.require(algorithm).build().verify(token);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
