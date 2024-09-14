package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Color;
import com.example.demo.services.ColorService;

@Controller
@RequestMapping("/color")
public class ColorController {

	@Autowired
	ColorService colorService;

	@PostMapping("/create")
	public String createColor(Color color, RedirectAttributes redirectAttributes) {
		colorService.createColor(color);
		redirectAttributes.addFlashAttribute("message", "Màu sắc mới đã được tạo.");
		return "redirect:/home/create/product";
	}
}
