package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Account;
import com.example.demo.entity.ChatConversation;
import com.example.demo.entity.Message;
import com.example.demo.repository.ChatConversationRepository;
import com.example.demo.repository.MessageRepository;
import com.example.demo.services.AccountService;
import com.example.demo.services.MessageService;

@Service
public class MessageServiceImpl implements MessageService{

	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private ChatConversationRepository chatConversationRepository;

	@Autowired
	private AccountRepository accountRepository;


	@Override
	public Message sendMessage(String senderUsername, Long ChatConversationId, String content) {
		if(ChatConversationId == null) {
			  throw new IllegalArgumentException("Conversation ID must not be null");
		}
		Account sender = accountRepository.findByUsername(senderUsername);
		if(sender == null) {
			  throw new IllegalArgumentException("Sender not found");
		}
		ChatConversation chatConversation = chatConversationRepository.findById(ChatConversationId)
				   .orElseThrow(() -> new IllegalArgumentException("Conversation not found"));
		
		Message message = new Message();
		message.setSender(sender);
		message.setChatConversation(chatConversation);
		message.setContent(content);
		message.setSentAt(LocalDateTime.now());
		
		return messageRepository.save(message);
	}

	@Override
	public List<Message> getMessagesByConversation(Long conversationId) {
		return messageRepository.findByChatConversationIdOrderBySentAtAsc(conversationId);
	}



}
