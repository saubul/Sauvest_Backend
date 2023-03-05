package sauprojects.sauvest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chat_messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
	
	@Id
	@SequenceGenerator(name = "chat_message_id_seq", sequenceName = "chat_message_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_message_id_seq")
	private Long id;
	
	private String content;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "user_id_fk"))
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "chat_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "chat_id_fk"))
	private Chat chat;
	
}
