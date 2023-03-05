package sauprojects.sauvest.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sauprojects.sauvest.dto.VoteDTO;
import sauprojects.sauvest.entity.User;
import sauprojects.sauvest.entity.Vote;
import sauprojects.sauvest.repository.VoteRepository;
import sauprojects.sauvest.service.PostService;
import sauprojects.sauvest.service.UserService;
import sauprojects.sauvest.service.VoteService;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
	
	private final VoteRepository voteRepository;
	private final PostService postService;
	private final UserService userService;
	
	@Override
	public List<VoteDTO> getAllVotesByPostId(Long postId) {
		return voteRepository.findByPostId(postId).stream().map(VoteDTO::buildVoteDTO).toList();
	}

	@Override
	public VoteDTO saveVote(VoteDTO voteDTO) {
		Optional<Vote> voteOptional = voteRepository.findByUserIdAndPostId(userService.findUserByUsername(voteDTO.getUsername()).getId(),
																		   voteDTO.getPostId());
		
		if(voteOptional.isPresent()) {
			Vote vote = voteOptional.get();
			voteRepository.delete(vote);
		} else {
			voteRepository.save(this.buildVote(voteDTO));
		}
		return voteDTO;
	}

	@Override
	public Vote buildVote(VoteDTO voteDTO) {
		return Vote.builder()
					   .post(postService.findPostById(voteDTO.getPostId()))
					   .user(userService.findUserByUsername(voteDTO.getUsername()))
				   .build();
	}
	
	@Override
	public Boolean isLikedByUsername(Long postId, String username) {
		User user = userService.findUserByUsername(username);
		Optional<Vote> vote = voteRepository.findByUserIdAndPostId(user.getId(), postId);
		if(vote.isEmpty()) {
			return false;
		}
		return true;
	}
	
}
