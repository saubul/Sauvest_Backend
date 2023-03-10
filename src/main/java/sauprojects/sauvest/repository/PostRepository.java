package sauprojects.sauvest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sauprojects.sauvest.entity.Post;
import sauprojects.sauvest.entity.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	
	List<Post> findByUser(User user);
	
}
