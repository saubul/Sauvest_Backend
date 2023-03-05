package sauprojects.sauvest.service;


import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import sauprojects.sauvest.dto.ProfileDTO;
import sauprojects.sauvest.dto.UserDTO;
import sauprojects.sauvest.entity.User;

public interface UserService extends UserDetailsService {
	
	UserDTO saveUser(User user);
	
	UserDTO saveUser(UserDTO userDTO);
	
	User findUserByUsername(String username);
	
	User findUserById(Long id);
	
	void enableUser(User user);
	
	User buildUser(UserDTO userDTO);
	
	List<UserDTO> getAllUsersBySubscriptionUserId(Long userId);
	
	List<ProfileDTO> getAllUsersBySurnameStartsWith(String string);

	UserDTO updateUser(UserDTO userDTO);
}
