package sauprojects.sauvest.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

	@Id
	@SequenceGenerator(name =  "comment_id_seq", sequenceName = "comment_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_id_seq")
	private Long id;
	
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "user_id_fk"))
	private User user;
	
	private String content;
	
	@CreationTimestamp
	@Column(name = "creation_date_time")
	private LocalDateTime creationDateTime;
	
	@ManyToOne(targetEntity = Post.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "post_id_fk"))
	private Post post;
}
