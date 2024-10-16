package com.example.demo.services;

import java.util.List;
import com.example.demo.entity.Message;

public interface MessageService {
	
	Message sendMessage(String senderUsername,Long ChatConversationId, String content);
	
	List<Message> getMessagesByConversation(Long conversationId);
	 
	 
}
