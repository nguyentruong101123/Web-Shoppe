package com.example.demo.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "Account")
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // ID là khóa chính, tự động tăng

    @Column(name = "username", unique = true, nullable = false)
    @NotEmpty(message = "Tên người dùng không được để trống")
    private String username;

    @Column(name = "password", nullable = false)
    @NotEmpty(message = "Mật khẩu không được để trống")
    private String password;

    @Column(nullable = false)
    @NotEmpty(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @Column(nullable = false)
    @NotEmpty(message = "Họ và tên không được để trống")
    private String fullname;
    
    @Column(name = "Photo")
    private String photo;  // Photo có thể để null

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private List<Authority> authorities;  // Danh sách quyền của tài khoản

    @JsonIgnore
    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    private UserProfile userProfile;  // Hồ sơ người dùng liên kết với tài khoản

    @JsonIgnore
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Comment> comments;  // Danh sách bình luận của người dùng

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "userOne", fetch = FetchType.LAZY)
    private List<ChatConversation> userOneChatConversations;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "userTwo", fetch = FetchType.LAZY)
    private List<ChatConversation> userTwoChatConversations;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private List<Message> messages;


	public Account(Integer id) {
		this.id = id;
	}
    
    
}
