package sauprojects.sauvest.entity;

import java.util.Collection;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chats", uniqueConstraints = {@UniqueConstraint(name = "chat_name_uq", columnNames = "name")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chat {
	
	@Id
	@SequenceGenerator(name = "chat_id_seq", sequenceName = "chat_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_id_seq")
	private Long id;
	
	@ManyToMany
	@JoinTable(name = "users_chats",
			   joinColumns = {@JoinColumn(name = "chat_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "chat_id"))},
			   inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "user_id"))})
	private Collection<User> users;
	
	@Column(nullable = false)
	private String name;
}
