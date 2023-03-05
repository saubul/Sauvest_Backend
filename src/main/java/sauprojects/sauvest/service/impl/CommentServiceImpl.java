package sauprojects.sauvest.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sauprojects.sauvest.dto.CommentDTO;
import sauprojects.sauvest.entity.Comment;
import sauprojects.sauvest.repository.CommentRepository;
import sauprojects.sauvest.service.CommentService;
import sauprojects.sauvest.service.PostService;
import sauprojects.sauvest.service.UserService;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
	
	private final CommentRepository commentRepository;
	private final UserService userService;
	private final PostService postService;

	@Override
	public Comment findCommentById(Long commentId) {
		Optional<Comment> commentOptional = commentRepository.findById(commentId);
		
		Comment comment = commentOptional.orElseThrow(() -> new RuntimeException(String.format("Comment with id '%s' not found.", commentId)));
		
		return comment;
	}
	
	@Override
	public List<CommentDTO> getAllCommentsByPost(Long postId) {
		return commentRepository.findAllByPostId(postId).stream().map(CommentDTO::buildCommentDTO).toList();
	}

	@Override
	public CommentDTO saveComment(CommentDTO commentDTO) {
		commentRepository.save(this.buildComment(commentDTO));
		return commentDTO;
	}

	@Override
	public CommentDTO saveComment(Comment comment) {
		return CommentDTO.buildCommentDTO(commentRepository.save(comment));
	}

	@Override
	public Comment buildComment(CommentDTO commentDTO) {
		return Comment.builder()
						  .user(userService.findUserByUsername(commentDTO.getUsername()))
						  .post(postService.findPostById(commentDTO.getPostId()))
						  .content(commentDTO.getContent())
					  .build();
	}
	
}
