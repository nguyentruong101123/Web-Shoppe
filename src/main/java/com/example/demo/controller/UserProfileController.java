//package com.example.demo.controller;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.example.demo.entity.UserProfile;
//import com.example.demo.service.impl.UserProfileServiceImpl;
//
//@Controller
//@RequestMapping("/user")
//public class UserProfileController {
//	@Autowired
//	private UserProfileServiceImpl profileServiceImpl;
//
////	@GetMapping("/profile/{userId}")
////	public String getUserProfile(@PathVariable Integer userId, Model model) {
////		Optional<UserProfile> userProfile = profileServiceImpl.getUserProfileByUserId(userId);
////		if (userProfile != null) {
////			model.addAttribute("userProfile", userProfile);
////		} else {
////			model.addAttribute("error", "UserProfile not found");
////		}
////		return "profile/user"; // Tên view Thymeleaf
////	}
//
//	
//	
//	
//}
