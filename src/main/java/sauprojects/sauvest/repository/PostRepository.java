package sauprojects.sauvest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sauprojects.sauvest.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

}
