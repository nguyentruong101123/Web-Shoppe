package com.example.demo.service.impl;

import java.util.List;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductAttribute;
import com.example.demo.repository.ProductDetailRepository;
import com.example.demo.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ProductImage;
import com.example.demo.repository.ProductImageRepository;
import com.example.demo.services.ProductImageService;

@Service
public class ProductImageServiceImpl implements ProductImageService {

	@Autowired
	private ProductImageRepository productImageRepository;

	@Autowired
	private ProductDetailRepository productDetailRepository;

	@Autowired
	private ProductRepository productRepository;

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

	@Override
	public ProductImage findByAttributeId(Integer id) {
		return productImageRepository.findFirstByAttributeId(id);
	}

	@Override
	public ProductImage updateAttributeId(ProductImage productImage) {
		Integer productAttributeId = productImage.getAttribute().getId(); // Lấy ID từ thuộc tính

		ProductAttribute productAttribute = productDetailRepository.findById(productAttributeId)
				.orElseThrow(() -> new EntityNotFoundException("Không tìm thấy thuộc tính sản phẩm với ID: " + productAttributeId));

		ProductImage productImageId = productImageRepository.findByIdAndAttributeId(productImage.getId(), productAttribute);
		if (productImageId == null) {
			throw new EntityNotFoundException("Không tìm thấy hình ảnh với ID: " + productImage.getId());
		}

		productImageId.setImage(productImage.getImage());
		productImageId.setImageType("detail");

		// Lưu lại vào repository
		return productImageRepository.save(productImageId);
	}

	@Override
	public ProductImage updateProductId(ProductImage productImage) {
		Integer productId = productImage.getProduct().getId();

		Product product = productRepository.findById(productId).get();

		ProductImage productImage1 = productImageRepository.findByIdAndProductId(productImage.getId(), product);
		if(productImage1 == null) {
			throw new EntityNotFoundException("Không tìm thấy hình ảnh với ID: " + productImage.getId());
		}
		productImage1.setImage(productImage.getImage());
		productImage1.setImageType("main");
		return productImageRepository.save(productImage1);
	}


}
