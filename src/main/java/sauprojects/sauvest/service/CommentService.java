package sauprojects.sauvest.service;

import java.util.List;

import sauprojects.sauvest.dto.CommentDTO;
import sauprojects.sauvest.entity.Comment;

public interface CommentService {

	Comment findCommentById(Long commentId);
	
	CommentDTO saveComment(CommentDTO commentDTO);
	
	CommentDTO saveComment(Comment comment);
	
	Comment buildComment(CommentDTO commentDTO);
	
	List<CommentDTO> getAllCommentsByPost(Long postId);

}
