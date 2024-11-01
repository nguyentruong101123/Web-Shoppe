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

	@Override
	public ProductImage updateProductImage(ProductImage productImage) {
		ProductImage productImageId = productImageRepository.findMainImageByProductId(productImage.getProduct().getId());
		productImage.setImage(productImageId.getImage());
		return productImageRepository.save(productImage);
	}

	@Override
	public List<ProductImage> findByProductAttributeId(Integer productId) {
		return productImageRepository.findByAttributeId(productId);
	}

	@Override
	public ProductImage deleteProductImage(Integer id) {
		ProductImage productImage = productImageRepository.findById(id).get();
		productImageRepository.delete(productImage);
		return productImage;
	}


}
