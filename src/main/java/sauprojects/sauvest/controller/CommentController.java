package sauprojects.sauvest.controller;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sauprojects.sauvest.dto.CommentDTO;
import sauprojects.sauvest.service.CommentService;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
	
	private final CommentService commentService;
	
	@PostMapping("/save")
	public HttpEntity<CommentDTO> saveComment(@RequestBody CommentDTO commentDTO) {
		return new ResponseEntity<CommentDTO>(commentService.saveComment(commentDTO), HttpStatus.OK);
	}
	
	@GetMapping("/getComments/{postId}")
	public HttpEntity<List<CommentDTO>> getAllCommentsForPost(@PathVariable Long postId) {
		
		return new ResponseEntity<>(commentService.getAllCommentsByPost(postId), HttpStatus.OK);
		
	}
	
}
