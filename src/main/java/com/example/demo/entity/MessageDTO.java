package com.example.demo.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MessageDTO {
	
	private String content;
	private String sender;
	private String avatarUrl;
	private Long conversationId;
	private String image;
	private LocalDateTime sentAt;

}
