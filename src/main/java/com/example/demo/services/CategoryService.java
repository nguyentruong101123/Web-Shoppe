package com.example.demo.services;

import java.util.List;

import com.example.demo.entity.Category;

public interface CategoryService {
	
	List<Category> findAll();
	
	Category save(Category category);

	Category findById(Integer id);


}
