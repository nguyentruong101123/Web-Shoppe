package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Account;
import com.example.demo.services.AccountService;

import jakarta.validation.Valid;

@Controller
public class RegisterUserController {

    @Autowired
    private AccountService accountService;

    
    @RequestMapping("/auth/sign-up")
	 public String showRegistrationForm(Model model) {
       model.addAttribute("account", new Account());
       return "security/register"; // Tên chính xác của template Thymeleaf
   }
    
    @PostMapping("/register/user")
    public String register(@Valid @ModelAttribute("account") Account account, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "security/register"; // Trả về form đăng ký nếu có lỗi
        }
        try {
        	accountService.registerUser(account);
            return "redirect:/auth/login/form"; // Điều hướng sau khi đăng ký thành công
        } catch (Exception e) {
            model.addAttribute("registrationError", "Đã xảy ra lỗi trong quá trình đăng ký. Vui lòng thử lại.");
            return "security/register"; // Trả về form đăng ký nếu có lỗi
        }
    
    }


}
