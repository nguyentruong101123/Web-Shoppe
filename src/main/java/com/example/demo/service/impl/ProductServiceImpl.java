package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductImage;
import com.example.demo.repository.ProductImageRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	private ProductImageRepository productImageRepository;

	@Override
	public List<Product> findByCategoryIdAndProductImageId(Integer categoryId, Integer productImageId) {
		return productRepository.findByCategoryIdAndProductImageId(categoryId, productImageId);
	}

	@Override
	public List<Product> findByCategoryId(Integer categoryId) {
		return productRepository.findByCategoryId(categoryId);
	}

	@Override
	public List<Product> getAll() {
		return productRepository.findAll();
	}

	@Override
	public Product getProductById(Integer productId) {
		return productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
	}

	@Override
	public List<Product> findByCategoryIdWithMainImage(Integer categoryId) {
		List<Product> products = productRepository.findByCategoryId(categoryId);
		for (Product product : products) {
			ProductImage mainImage = productImageRepository.findMainImageByProductId(product.getId());
			product.setMainImage(mainImage);
		}
		return products;
	}

	@Override
	public List<Product> getAllWithMainImage() {
		List<Product> products = productRepository.findAll();
		for (Product product : products) {
			ProductImage mainImage = productImageRepository.findMainImageByProductId(product.getId());
			product.setMainImage(mainImage);
		}
		return products;
	}

	@Override
	public Product createProduct(Product product) {
		return productRepository.save(product);
	}

}
