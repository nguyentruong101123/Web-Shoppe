package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Account;
import com.example.demo.entity.ChatConversation;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.ChatConversationRepository;
import com.example.demo.services.AccountService;
import com.example.demo.services.ChatConversationService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ChatConversationServiceImpl implements ChatConversationService{

	@Autowired
	private ChatConversationRepository chatConversationRepository;
	
	@Autowired 
	private AccountRepository accountRepository;
	
	@Override
	public ChatConversation createChatConversation(String userOneUsername, String userTwoUsername) {
		Account userOne = accountRepository.findByUsername(userOneUsername);
		Account userTwo = accountRepository.findByUsername(userTwoUsername);
		
	    ChatConversation conversation = chatConversationRepository.findByUserOne_UsernameAndUserTwo_Username(userOneUsername, userTwoUsername);
	    if (conversation == null) {
	        conversation = chatConversationRepository.findByUserTwo_UsernameAndUserOne_Username(userOneUsername, userTwoUsername);
	    }

	    // Nếu đã tìm thấy cuộc trò chuyện, trả về ngay lập tức
	    if (conversation != null) {
	        return conversation;
	    }

		
		
		ChatConversation chatConversation = new ChatConversation();
		chatConversation.setUserOne(userOne);
		chatConversation.setUserTwo(userTwo);
		chatConversation.setCreatedAt(LocalDateTime.now());
		
		return chatConversationRepository.save(chatConversation);
		
	}

	@Override
	public List<ChatConversation> getUserConversations(String username) {
		return chatConversationRepository.findByUserOne_UsernameOrUserTwo_Username(username, username);
	}

	@Override
	public ChatConversation getConversation(Long id) {
		return chatConversationRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Conversation not found"));
	}

	@Override
	public ChatConversation findConversation(String userOneUsername, String userTwoUsername) {
		ChatConversation conversation = chatConversationRepository.findByUserOne_UsernameAndUserTwo_Username(userOneUsername, userTwoUsername);
		if(conversation == null) {
			conversation= chatConversationRepository.findByUserTwo_UsernameAndUserOne_Username(userTwoUsername, userOneUsername);
		}
		return conversation;
	}

}
