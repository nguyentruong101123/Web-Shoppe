package com.example.demo.repository;

import com.example.demo.entity.ChatConversation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatConversationRepository extends JpaRepository<ChatConversation, Long> {
	
	Optional<ChatConversation> findById(Long id);
	
	List<ChatConversation> findByUserOne_UsernameOrUserTwo_Username(String userOneUsername, String userTwoUsername);
	
	ChatConversation findByUserOne_UsernameAndUserTwo_Username(String userOneUsername, String userTwoUsername);
	
	ChatConversation findByUserTwo_UsernameAndUserOne_Username(String userTwoUsername, String userOneUsername);
	
	@Query("select c from ChatConversation c where :username = c.userOne.username or :username = c.userTwo.username")
	List<ChatConversation> findChatConversationByUsername(@Param("username") String username);

}
