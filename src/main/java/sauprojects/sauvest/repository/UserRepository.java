package sauprojects.sauvest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sauprojects.sauvest.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);
	
}
