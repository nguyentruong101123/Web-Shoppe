package com.example.demo.services;

import java.util.List;

import com.example.demo.entity.ChatConversation;

public interface ChatConversationService {
	
	ChatConversation createChatConversation(String userOneUsername, String userTwoUsername);
	
	List<ChatConversation> getUserConversations(String username);
	
	ChatConversation getConversation(Long id);
	
	ChatConversation findConversation(String userOneUsername, String userTwoUsername);
	
	

}
