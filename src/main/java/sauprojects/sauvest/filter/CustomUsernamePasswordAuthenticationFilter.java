package sauprojects.sauvest.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private final AuthenticationManager authenticationManager;
	
	private final Long jwtExpirationTime;
	
	private final String secret;
	
	public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager,
													  String jwtExpirationTime,
													  String secret) {
		this.authenticationManager = authenticationManager;
		this.jwtExpirationTime = Long.parseLong(jwtExpirationTime);
		this.secret = secret;
		this.setFilterProcessesUrl("/api/auth/signIn");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		if(!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		
		String username = this.obtainUsername(request);
		username = (username != null) ? username.trim() : "";
		
		String password = this.obtainPassword(request);
		password = (password != null) ? password.trim() : "";
		
		UsernamePasswordAuthenticationToken authentication = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
		return this.authenticationManager.authenticate(authentication);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		User user = (User) authResult.getPrincipal();
		
		Algorithm algorithm = Algorithm.HMAC256(this.secret);
		
		Date accessTokenExpiresAtDate = new Date(System.currentTimeMillis() + jwtExpirationTime);
		
		String accessToken = JWT.create()
									.withExpiresAt(accessTokenExpiresAtDate)
									.withClaim("username", user.getUsername())
									.withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
								.sign(algorithm);
		
		Date refreshTokenExpiresAtDate = new Date(System.currentTimeMillis() + jwtExpirationTime * 10);
		String refreshToken = JWT.create()
									.withExpiresAt(refreshTokenExpiresAtDate)
									.withClaim("username", user.getUsername())
									.withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
								.sign(algorithm);

		Map<String, String> responseMap = new HashMap<String, String>();
		
		responseMap.put("username", user.getUsername());
		responseMap.put("accessToken", accessToken);
		responseMap.put("refreshToken", refreshToken);
		
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
		
	}
	
	

}
