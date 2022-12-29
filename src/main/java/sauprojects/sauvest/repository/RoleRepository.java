package sauprojects.sauvest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sauprojects.sauvest.entity.Role;
import sauprojects.sauvest.entity.RoleEnum;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	Optional<Role> findByRoleName(RoleEnum roleEnum);
	
}
