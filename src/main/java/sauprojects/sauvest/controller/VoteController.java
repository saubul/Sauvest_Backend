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
import sauprojects.sauvest.dto.VoteDTO;
import sauprojects.sauvest.service.VoteService;

@RestController
@RequestMapping("/api/vote")
@RequiredArgsConstructor
public class VoteController {
	
	private final VoteService voteService;
	
	@GetMapping("/post/{postId}")
	public HttpEntity<List<VoteDTO>> getVotesByPost(@PathVariable("postId") Long postId) {
		return new ResponseEntity<List<VoteDTO>>(voteService.getAllVotesByPostId(postId), HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public HttpEntity<VoteDTO> vote(@RequestBody VoteDTO voteDTO) {
		
		return new ResponseEntity<>(voteService.saveVote(voteDTO), HttpStatus.CREATED);
		
	}
	
	@GetMapping("/isLiked")
	public HttpEntity<Boolean> isLikedByUser(@RequestParam("postId") Long postId, @RequestParam("username") String username) {
		return new ResponseEntity<Boolean>(voteService.isLikedByUsername(postId, username),HttpStatus.OK);
	}
	
}
