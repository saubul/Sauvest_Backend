package sauprojects.sauvest.entity;

import java.time.LocalDateTime;
import java.util.Collection;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

	@Id
	@SequenceGenerator(name =  "post_id_seq", sequenceName = "post_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_id_seq")
	private Long id;
	
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "user_id_fk"))
	private User user;
	
	@Column(length = 500)
	private String content;
	
	@CreationTimestamp
	@Column(name = "creation_date_time")
	private LocalDateTime creationDateTime;
	
	@Column(name = "img_path")
	private String imgPath;
	
	@OneToMany(targetEntity = Comment.class, mappedBy = "post")
	private Collection<Comment> comments;
	
}
