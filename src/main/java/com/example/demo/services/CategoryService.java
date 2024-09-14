package com.example.demo.services;

import java.util.List;

import com.example.demo.entity.Category;

public interface CategoryService {
	
	public List<Category> findAll();
	
	public Category save(Category category);
}
