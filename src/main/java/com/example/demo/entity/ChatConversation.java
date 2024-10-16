package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "chat_conversation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatConversation implements Serializable {
 
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_one_id", referencedColumnName = "username")
    private Account userOne;

    @ManyToOne
    @JoinColumn(name = "user_two_id", referencedColumnName = "username")
    private Account userTwo;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @ToString.Exclude
    @OneToMany(mappedBy = "chatConversation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> messages;


}
