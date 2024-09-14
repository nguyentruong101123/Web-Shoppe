package com.example.demo.services;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity.Account;

import lombok.Data;

@Data
public class CustomUserDetail implements UserDetails {

	private static final long serialVersionUID = 1L;
	private final Account account;

	public CustomUserDetail(Account account) {
		this.account = account;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return account.getAuthorities().stream()
				.map(authority -> new SimpleGrantedAuthority("ROLE_" + authority.getRole().getId()))
				.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return account.getPassword();
	}

	@Override
	public String getUsername() {
		return account.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true; // Thay đổi nếu cần
	}

	@Override
	public boolean isAccountNonLocked() {
		return true; // Thay đổi nếu cần
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true; // Thay đổi nếu cần
	}

	@Override
	public boolean isEnabled() {
		return true; // Thay đổi nếu cần
	}

}
