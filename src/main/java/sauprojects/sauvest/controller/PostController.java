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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sauprojects.sauvest.dto.PostDTO;
import sauprojects.sauvest.service.PostService;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
	
	private final PostService postService;
	
	@PostMapping("/save")
	public HttpEntity<PostDTO> savePost(@RequestBody PostDTO postDTO) {
		return new ResponseEntity<PostDTO>(postService.savePost(postDTO), HttpStatus.OK);
	}
	
	@GetMapping("/get/{postId}")
	public HttpEntity<PostDTO> getPostById(@PathVariable("postId") Long postId) {
		return new ResponseEntity<PostDTO>(postService.getPostById(postId), HttpStatus.OK);
	}
	
	@GetMapping("/getAll")
	public HttpEntity<List<PostDTO>> getAllPosts() {
		return new ResponseEntity<List<PostDTO>>(postService.getAllPosts(), HttpStatus.OK);
	}
	
	@GetMapping("/getAllByUser")
	public HttpEntity<List<PostDTO>> getAllPostsByUsername(@RequestParam("username") String username) {
		return new ResponseEntity<List<PostDTO>>(postService.getAllPostsByUsername(username), HttpStatus.OK);
	}
}
