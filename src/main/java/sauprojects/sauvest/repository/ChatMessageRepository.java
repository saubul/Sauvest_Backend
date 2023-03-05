package sauprojects.sauvest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sauprojects.sauvest.entity.ChatMessage;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>{
	
	List<ChatMessage> findAllByChatId(Long chatId);
	
}
