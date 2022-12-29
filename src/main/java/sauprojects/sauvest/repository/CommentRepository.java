package sauprojects.sauvest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sauprojects.sauvest.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

}
