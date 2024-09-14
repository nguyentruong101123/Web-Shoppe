package com.example.demo.service.impl;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Account;
import com.example.demo.entity.Authority;
import com.example.demo.entity.ProductImage;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.AuthorityRepositoty;
import com.example.demo.repository.RoleRepository;
import com.example.demo.services.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	private AuthorityRepositoty authorityRepository;

	@Autowired
	private RoleRepository roleRepository;


	 
	
	@Override
	public Account registerUser(Account account) throws Exception {
		try {
			if (accountRepository.findByUsername(account.getUsername()) != null) {
				throw new Exception("Username đã tồn tại.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (accountRepository.findByEmail(account.getEmail()) != null) {
				throw new Exception("Email đã tồn tại.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Account newAccount = new Account();
		newAccount.setUsername(account.getUsername());
		newAccount.setPassword(passwordEncoder.encode(account.getPassword()));
		newAccount.setEmail(account.getEmail());
		newAccount.setFullname(account.getFullname());

		accountRepository.save(newAccount);

		Authority authority = new Authority();
		authority.setAccount(newAccount);
		authority.setRole(roleRepository.findById("USER").orElseThrow(() -> new RuntimeException("Role not found")));

		authorityRepository.save(authority);
		return newAccount;
	}

	@Override
	public Account findAccountByUsername(String username) {
		return accountRepository.findByUsername(username);
	}

	@Override
	public Account findAccountByEmail(String email) {
		return accountRepository.findByEmail(email);
	}

	@Override
	public Account updateAccount(Integer id, Account account) throws Exception {
		Optional<Account> user = accountRepository.findById(id);
		if (user.isPresent()) {
			Account newAccount = user.get();
			newAccount.setEmail(account.getEmail());
			newAccount.setFullname(account.getFullname());

			if (account.getPassword() != null && !account.getPassword().isEmpty()) {
				newAccount.setPassword(passwordEncoder.encode(account.getPassword()));
			}

			Account updatedAccount = accountRepository.save(newAccount);
			System.out.println("Updated Account: " + updatedAccount); // Logging for debugging
			return updatedAccount;
		} else {
			throw new RuntimeException("Account not found with ID: " + id);
		}

	}

	@Override
	public Account findById(Integer id) {
		Optional<Account> account = accountRepository.findById(id);
		return account.orElse(null); //
	}

	@Override
	public Account update(Account account) throws IOException {
		Account user = accountRepository.findByUsername(account.getUsername());
		if (user != null) {
			user.setEmail(account.getEmail());
			user.setFullname(account.getFullname());
			
			
			if (account.getPassword() != null && !account.getPassword().isEmpty()) {
				user.setPassword(passwordEncoder.encode(account.getPassword()));
			}
			if (account.getPhoto() != null && !account.getPhoto().isEmpty()) {
				user.setPhoto(account.getPhoto());
		    }
			Account updatedAccount = accountRepository.save(user);
			return updatedAccount;
		} else {
			throw new RuntimeException("Account not found with ID: " + account);
		}
	}
	
}
