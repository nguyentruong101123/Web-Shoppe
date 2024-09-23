package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.component.UserServices;
import com.example.demo.services.ProductService;

@Controller
public class CartController {

	@Autowired
	private UserServices services;
	
	@RequestMapping("/cart/sale")
	public String cart(Model model) {
		services.addUserDetailsToModel(model);
		model.addAttribute("hideHeader", true);
		return "cart/cart";
	}

}
