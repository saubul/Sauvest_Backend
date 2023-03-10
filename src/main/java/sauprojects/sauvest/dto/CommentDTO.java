package sauprojects.sauvest.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sauprojects.sauvest.entity.Comment;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {
	
	Long id;
	Long postId;
	String username;
	String content;
	LocalDateTime creationDateTime;
	
	public static CommentDTO buildCommentDTO(Comment comment) {
		return CommentDTO.builder()
							 .id(comment.getId())
							 .username(comment.getUser().getUsername())
							 .postId(comment.getPost().getId())
							 .content(comment.getContent())
							 .creationDateTime(comment.getCreationDateTime())
						 .build();
	}
}
