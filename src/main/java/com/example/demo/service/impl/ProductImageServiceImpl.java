package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ProductImage;
import com.example.demo.repository.ProductImageRepository;
import com.example.demo.services.ProductImageService;

@Service
public class ProductImageServiceImpl implements ProductImageService {

	@Autowired
	private ProductImageRepository productImageRepository;

	@Override
	public List<ProductImage> getImagesByProductId(Integer productId) {
		return productImageRepository.findByProductId(productId);
	}

	@Override
	public ProductImage getMainImageByProductId(Integer productId) {
		return productImageRepository.getMainImageByProductId(productId);
	}

	@Override
	public ProductImage createProductImage(ProductImage productImage) {
		return productImageRepository.save(productImage);
	}

}
