package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Account;
import com.example.demo.services.UserService;


@Controller
@RequestMapping("/auth")
public class AdminController {
	@Autowired
	private UserService userService;

	@RequestMapping("/login/form")
	public String login() {
		return "security/login";
	}


	@RequestMapping("/login/success")
	public String success(Model model) {
		model.addAttribute("message", "Đăng nhập thành công");
		return "redirect:/home/index";
	}

	@RequestMapping("/logoff/success")
	public String logoffSuccess(Model model) {
		model.addAttribute("message", "Đăng xuất thành công");
		return "redirect:/home/index";
	}

	@RequestMapping("/login/error")
	public String error(Model model) {
		model.addAttribute("message", "Sai thông tin Đăng nhập");
		return "forward:/auth/login/form";
	}

	@RequestMapping("/auth/access/denied")
	public String accessDenied(Model model) {
		model.addAttribute("message", "Bạn không có quyền truy xuất");
		return "security/login";
	}

	@RequestMapping("/oauth2/login/success")
	public String oauth2Success(OAuth2AuthenticationToken oauth2, Model model) {
		userService.loginFormOauth2(oauth2);

		OAuth2User oAuth2User = oauth2.getPrincipal();
		String username = oAuth2User.getAttribute("email");

		model.addAttribute("username", username);
		model.addAttribute("message", "Đăng nhập thành công từ OAuth2");

		// Chuyển hướng đến trang thành công của đăng nhập
		return "redirect:/home/index";
	}
}
