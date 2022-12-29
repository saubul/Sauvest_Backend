package sauprojects.sauvest.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sauprojects.sauvest.dto.UserDTO;
import sauprojects.sauvest.entity.Role;
import sauprojects.sauvest.entity.User;
import sauprojects.sauvest.repository.UserRepository;
import sauprojects.sauvest.service.RoleService;
import sauprojects.sauvest.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleService roleService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOptional = userRepository.findByUsername(username);
		
		User user = userOptional.orElseThrow(() -> new UsernameNotFoundException(String.format("User with '%s' username not found", username)));
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}

	@Override
	public UserDTO saveUser(User user) {
		return UserDTO.buildUserDTO(userRepository.save(user));
	}

	@Override
	public UserDTO saveUser(UserDTO userDTO) {
		userRepository.save(this.buildUser(userDTO));
		return userDTO;
	}
	
	@Override
	public User findUserByUsername(String username) {
		Optional<User> userOptional = userRepository.findByUsername(username);
		
		User user = userOptional.orElseThrow(() -> new UsernameNotFoundException(String.format("User with username '%s' not found.", username)));
		
		return user;
	}

	@Override
	public User findUserById(Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		
		User user = userOptional.orElseThrow(() -> new RuntimeException(String.format("User with id '%s' not found.", id)));
		
		return user;
	}

	@Override
	public void enableUser(User user) {
		user.setEnabled(true);
		userRepository.save(user);
	}

	@Override
	public String saveUserImg(String username, String filename) {
		User user = this.findUserByUsername(username);
		user.setImgPath(filename);
		this.saveUser(user);
		return filename;
	}
	
	@Override
	public User buildUser(UserDTO userDTO) {
		return User.builder()
					   .username(userDTO.getUsername())
					   .password(passwordEncoder.encode(userDTO.getPassword()))
					   .email(userDTO.getEmail())
					   .name(userDTO.getName())
					   .surname(userDTO.getSurname())
					   .enabled(false)
					   .roles(new HashSet<Role>(Arrays.asList(roleService.findRoleByRoleName("ROLE_USER"))))
					   .imgPath(userDTO.getImgPath())
				   .build();
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName().name())).collect(Collectors.toList());
	}
	
}
