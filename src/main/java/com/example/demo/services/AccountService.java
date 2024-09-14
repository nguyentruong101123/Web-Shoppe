package com.example.demo.services;



import java.io.IOException;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Account;

public interface AccountService {
	
	public Account registerUser(Account account) throws Exception;
	
	public Account findAccountByUsername(String username);
	
	public Account findAccountByEmail(String email) ;
	
	@Secured("hasRole('USER')")
	public Account updateAccount(Integer id, Account account) throws Exception;
	
	public Account findById(Integer Id);
	
	public Account update(Account account) throws IOException;
	
}
