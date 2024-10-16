package com.example.demo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
	
	 Account findByEmail(String email);
	 Account findByUsername(String username);
	 List<Account> findByFullname(String fullname);
}
