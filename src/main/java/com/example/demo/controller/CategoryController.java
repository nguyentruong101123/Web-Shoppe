package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Category;
import com.example.demo.services.CategoryService;

@Controller
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	@PostMapping("/create")
	public String createCategory(Category category, RedirectAttributes redirectAttributes) {
		categoryService.save(category);
		redirectAttributes.addFlashAttribute("message", "Danh mục mới đã được tạo.");
		return "redirect:/home/service/product";
	}
}
