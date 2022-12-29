package sauprojects.sauvest.service;

import sauprojects.sauvest.entity.Role;

public interface RoleService {
	
	Role findRoleByRoleName(String roleName);
	
}
