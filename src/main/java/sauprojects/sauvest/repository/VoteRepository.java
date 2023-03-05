package sauprojects.sauvest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sauprojects.sauvest.entity.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long>{

	List<Vote> findByPostId(Long postId);	
	
	Optional<Vote> findByUserIdAndPostId(Long userId, Long postId);
	
}
