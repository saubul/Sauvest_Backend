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
	
	String username;
	String content;
	String imgPath;
	LocalDateTime creationDateTime;
	
	public static PostDTO buildPostDTO(Post post) {
		return PostDTO.builder()
						  .username(post.getUser().getUsername())
						  .content(post.getContent())
						  .imgPath(post.getImgPath())
						  .creationDateTime(post.getCreationDateTime())
					  .build();
	}
	
}
