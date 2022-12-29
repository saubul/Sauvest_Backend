package sauprojects.sauvest.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sauprojects.sauvest.entity.Role;
import sauprojects.sauvest.entity.RoleEnum;
import sauprojects.sauvest.repository.RoleRepository;
import sauprojects.sauvest.service.RoleService;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;
	
	@Override
	public Role findRoleByRoleName(String roleName) {
		Optional<Role> roleOptional = roleRepository.findByRoleName(RoleEnum.valueOf(roleName));
		
		Role role = roleOptional.orElseThrow(() -> new RuntimeException(String.format("Role with name '%s' not found.", roleName)));
		
		return role;
	}

}
