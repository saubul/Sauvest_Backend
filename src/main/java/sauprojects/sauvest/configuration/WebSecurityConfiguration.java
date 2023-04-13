package sauprojects.sauvest.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;
import sauprojects.sauvest.filter.CustomUsernamePasswordAuthenticationFilter;
import sauprojects.sauvest.filter.JWTAuthenticationFilter;
import sauprojects.sauvest.service.UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

	@Value("${jwt.expirationTime}")
	private String jwtExpirationTime;
	
	@Value("${jwt.secret}")
	private String secret;
	
	private final PasswordEncoder passwordEncoder;
	private final UserService userService;
	private final AuthenticationConfiguration authenticationConfiguration;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity
			.csrf().disable()
			.cors().and()
			.sessionManagement(sessionManagementCustomizer -> sessionManagementCustomizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authenticationProvider(daoAuthenticationProvider())
			.formLogin(log -> log.disable())
			.addFilter(new CustomUsernamePasswordAuthenticationFilter(this.authenticationManager(), jwtExpirationTime, secret))
			.addFilterBefore(new JWTAuthenticationFilter(secret), UsernamePasswordAuthenticationFilter.class)
			.authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/**",
																"/api/post/getAll",
																"/api/marketdata/**",
																"/error",
																"/v2/api-docs",
																"/configuration/ui",
																"/configuration/security",
											                	"/webjars/**",
											                	"/ws/**").permitAll()
											   .anyRequest().authenticated());
		
		return httpSecurity.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public AuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		return daoAuthenticationProvider;
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:4200/"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
	}
	
}
