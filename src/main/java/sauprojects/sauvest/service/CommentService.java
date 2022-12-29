package sauprojects.sauvest.service;

import sauprojects.sauvest.dto.CommentDTO;
import sauprojects.sauvest.entity.Comment;

public interface CommentService {

	Comment findCommentById(Long commentId);
	
	CommentDTO saveComment(CommentDTO commentDTO);
	
	CommentDTO saveComment(Comment comment);
	
	Comment buildComment(CommentDTO commentDTO);
	
}
