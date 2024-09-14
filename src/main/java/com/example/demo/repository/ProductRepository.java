package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	@Query("SELECT p FROM Product p JOIN p.productImages pi WHERE p.category.id = ?1 AND pi.id = ?2")
	List<Product> findByCategoryIdAndProductImageId(Integer categoryId, Integer productImageId);

	@Query("SELECT p FROM Product p JOIN FETCH p.productImages pi WHERE p.category.id = ?1 AND pi.imageType = 'main'")
	List<Product> findByCategoryId(Integer categoryId);

	
}
