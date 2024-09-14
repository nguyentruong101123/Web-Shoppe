package com.example.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;

@Service
public class UserService implements UserDetailsService{

	@Autowired AccountRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 Account user = repository.findByUsername(username);
		          if(user == null) {
		        	  throw new UsernameNotFoundException("User not found with username: " + username);
		          }
		        
		        return new CustomUserDetail(user);
	}
	
	public void loginFormOauth2(OAuth2AuthenticationToken oauth2) {
		OAuth2User oAuth2User = oauth2.getPrincipal();
		String email = (String) oAuth2User.getAttributes().get("email");
		String name = (String) oAuth2User.getAttributes().get("name");
		String picture = (String) oAuth2User.getAttributes().get("picture");
		List<SimpleGrantedAuthority> authorities = oauth2.getAuthorities().stream()
				.map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
				.collect(Collectors.toList());
		Account account = new Account();
		account.setUsername(email);
		account.setFullname(name);
		account.setPhoto(picture);
		
		CustomUserDetail userDetail = new CustomUserDetail(account);
		Authentication authRequest = new UsernamePasswordAuthenticationToken(userDetail, null, authorities);
		SecurityContextHolder.getContext().setAuthentication(authRequest);
		
	}

}
