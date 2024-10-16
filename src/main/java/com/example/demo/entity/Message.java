package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "message")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


    @ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "chat_conversation_id", nullable = false)
	private ChatConversation chatConversation;

	@ManyToOne
	@JoinColumn(name ="sender_id", referencedColumnName = "username")
	private Account sender;

	private String content;

	private String image;

	@Column(name = "sent_at")
	private LocalDateTime sentAt;


}