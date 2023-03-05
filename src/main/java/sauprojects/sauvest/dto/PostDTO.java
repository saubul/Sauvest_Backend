package sauprojects.sauvest.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sauprojects.sauvest.entity.Post;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {
	
	Long id;
	String username;
	String content;
	String imgPath;
	LocalDateTime creationDateTime;
	Integer voteCount;
	String name;
	String surname;
	
	public static PostDTO buildPostDTO(Post post) {

		return PostDTO.builder()
						  .id(post.getId())
						  .username(post.getUser().getUsername())
						  .content(post.getContent())
						  .imgPath(post.getImgPath())
						  .creationDateTime(post.getCreationDateTime())
						  .voteCount(post.getVotes().size())
						  .name(post.getUser().getName())
						  .surname(post.getUser().getSurname())
					  .build();
	}
	
}
