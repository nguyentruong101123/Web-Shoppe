package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.component.UserServices;
import com.example.demo.entity.Account;
import com.example.demo.entity.ChatConversation;
import com.example.demo.entity.Message;
import com.example.demo.entity.MessageDTO;
import com.example.demo.services.AccountService;
import com.example.demo.services.ChatConversationService;
import com.example.demo.services.MessageService;

import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/api/messages")
public class ChatController {

    @Autowired
    private MessageService messageService;

	@Autowired
	private UserServices services;
	
	@Autowired
	private ChatConversationService chatConversationService;
	
	@Autowired
	private AccountService accountService;
	
	// Trả về trang chat
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/chat")
	public String chatPage(Model model) {
		services.addUserDetailsToModel(model);
		model.addAttribute("hideHeader", true);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		List<ChatConversation> chatConversations = chatConversationService.getUserConversations(username);
		if(chatConversations == null) {
			throw new IllegalArgumentException("Conversation not found");
		}
		model.addAttribute("chatConversations", chatConversations);
		
		
		return "message/chat"; // Trả về tệp chat.html
	}
	
	@GetMapping("/chat/{recipientId}")
	public String chat(@PathVariable("recipientId") String recipientId, Model model) {
		services.addUserDetailsToModel(model);
		model.addAttribute("hideHeader", true);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String senderUsername = authentication.getName();
		
		ChatConversation conversation = chatConversationService.findConversation(senderUsername, recipientId);
		if(conversation == null) {
			conversation = chatConversationService.createChatConversation(senderUsername, recipientId);
		}

        model.addAttribute("conversation", conversation);
        
        List<Message> messages = messageService.getMessagesByConversation(conversation.getId());
        model.addAttribute("message",messages);
        
        List<ChatConversation> chatConversations = chatConversationService.getUserConversations(senderUsername);
        
        model.addAttribute("chatConversations", chatConversations);
        
		
		return "message/chat";
	}
	
	@GetMapping("/search")
	public String search(@RequestParam(value = "fullname", required = false) String fullname,  Model model) {
		services.addUserDetailsToModel(model);
		model.addAttribute("hideHeader", true);
		List<Account> search;
		if(fullname != null && !fullname.isEmpty()) {
			search = accountService.findByFullName(fullname);
		}else {
			search = accountService.getAll();
		}
		model.addAttribute("account",search);
		model.addAttribute("searchItem", fullname);
		return "message/chat";
	}
	
	
	
	@MessageMapping("/chat.sendMessage")
	@SendTo("/topic/public")
	@Transactional
	public MessageDTO sendMessage(@Payload Message message,Authentication authentication) throws IOException{
	
	    String senderUsername = authentication.getName();
		Long conversationId = message.getChatConversation().getId();
		String content = message.getContent();
		
		Message sentMessage = messageService.sendMessage(senderUsername, conversationId, content);
	
		
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setSender(sentMessage.getSender().getUsername());
		messageDTO.setAvatarUrl(sentMessage.getSender().getPhoto());
		messageDTO.setContent(sentMessage.getContent());
		messageDTO.setImage(sentMessage.getImage());
		messageDTO.setConversationId(sentMessage.getChatConversation().getId());
		
		return messageDTO;
		
	}
	

}
