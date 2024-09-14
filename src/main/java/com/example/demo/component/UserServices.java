package com.example.demo.component;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.example.demo.entity.Account;
import com.example.demo.service.impl.AccountServiceImpl;


@Component
public class UserServices {

	@Autowired
    private AccountServiceImpl accountServiceImpl;

	
    public void addUserDetailsToModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Account account = accountServiceImpl.findAccountByUsername(username);
        if (account != null) {
            model.addAttribute("account", account);
        } else {
            model.addAttribute("account", null);
        }
    }
    
    
}
