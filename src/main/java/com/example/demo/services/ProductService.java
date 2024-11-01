package com.example.demo.services;

import java.util.List;

import com.example.demo.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;

import com.example.demo.entity.Product;

public interface ProductService {
	
	List<Product> findByCategoryIdAndProductImageId(Integer categoryId, Integer productImageId);
	
	Page<Product> findByCategoryId(Integer categoryId, Pageable pageable);
	
	List<Product> getAll();
	
	Product getProductById(Integer productId);
	
	Page<Product> findByCategoryIdWithMainImage(Integer categoryId, Pageable pageable);

	Page<Product> getAllWithMainImage(Pageable pageable);

	Page<Product> findByUser(Account account, Pageable pageable);
	
	@Secured("hasRole('ADMIN')")
	Product createProduct(Product product);

	Product update(Product product);
	
	int getTotalPages(int pageSize);

	Product delete (Integer productId);
}
