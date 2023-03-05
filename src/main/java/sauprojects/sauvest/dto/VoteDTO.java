package sauprojects.sauvest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sauprojects.sauvest.entity.Vote;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteDTO {
	
	private String username;
	private Long postId;
	
	public static VoteDTO buildVoteDTO(Vote vote) {
		return VoteDTO.builder()
						  .username(vote.getUser().getUsername())
						  .postId(vote.getPost().getId())
					  .build();
	}
	
}
