package sauprojects.sauvest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sauprojects.sauvest.entity.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long>{
	
	@Query("select c from Chat c join c.users u where u.id = :userId")
	List<Chat> findByUserId(@Param("userId") Long userId);
	
	Optional<Chat> findByName(String name);

}
