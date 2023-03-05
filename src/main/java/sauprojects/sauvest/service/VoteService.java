package sauprojects.sauvest.service;

import java.util.List;

import sauprojects.sauvest.dto.VoteDTO;
import sauprojects.sauvest.entity.Vote;

public interface VoteService {

	List<VoteDTO> getAllVotesByPostId(Long postId);
	
	VoteDTO saveVote(VoteDTO voteDTO);
	
	Vote buildVote(VoteDTO voteDTO);
	
	Boolean isLikedByUsername(Long postId, String username);
	
}
