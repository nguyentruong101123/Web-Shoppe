package com.example.demo.services;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;

import com.example.demo.entity.Product;

public interface ProductService {
	
	public List<Product> findByCategoryIdAndProductImageId(Integer categoryId, Integer productImageId);
	
	public List<Product> findByCategoryId(Integer categoryId, Pageable pageable);
	
	public List<Product> getAll();
	
	public Product getProductById(Integer productId);
	
	public List<Product> findByCategoryIdWithMainImage(Integer categoryId, Pageable pageable);

	public List<Product> getAllWithMainImage(Pageable pageable);
	
	@Secured("hasRole('ADMIN')")
	public Product createProduct(Product product);
	
	int getTotalPages(int pageSize); 
}
