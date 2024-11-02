package com.example.demo.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.example.demo.entity.ProductImage;
import com.example.demo.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ProductAttribute;
import com.example.demo.repository.ProductDetailRepository;
import com.example.demo.services.ProductDetailService;

@Service
public class ProductDetailServiceImpl implements ProductDetailService{

	@Autowired
	ProductDetailRepository detailRepository;

	@Autowired
	private ProductImageRepository productImageRepository;
    @Autowired
    private ProductDetailRepository productDetailRepository;

	@Override
	public List<ProductAttribute> findByProductId(Integer id) {
		List<ProductAttribute> productAttributes = detailRepository.findByProductId(id);

		// Nếu có sản phẩm, lấy hình ảnh cho sản phẩm đầu tiên
		for (ProductAttribute productAttribute : productAttributes) {
			// Lấy hình ảnh chính từ danh sách hình ảnh
			List<ProductImage> images = productImageRepository.findByAttributeId(productAttribute.getId());
			productAttribute.setProductImages(images);

			// Giả sử hình ảnh đầu tiên là hình ảnh chính
			if (!images.isEmpty()) {
				productAttribute.setMainImage(images.get(0)); // Gán hình ảnh chính
			}
		}

		return productAttributes;
	}

	@Override
	public ProductAttribute createProductAttribute(ProductAttribute productAttribute) {
		return detailRepository.save(productAttribute);
	}

	@Override
	public ProductAttribute findById(Integer id) {
		ProductAttribute productAttribute = detailRepository.findById(id).orElseThrow(null);
		return productAttribute;
	}

	@Override
	public ProductAttribute deleteProductAttribute(Integer id) {
		ProductAttribute productAttribute = detailRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Product not found with id: " + id));;
		detailRepository.delete(productAttribute);
		return productAttribute;
	}

	@Override
	public ProductAttribute updateProductAttribute(ProductAttribute productAttribute) {
		ProductAttribute productAttributeId = productDetailRepository.findById(productAttribute.getId()).get();
			productAttributeId.setColor(productAttribute.getColor());
			productAttribute.setSize(productAttribute.getSize());
			productAttribute.setPrice(productAttribute.getPrice());
			productAttribute.setStock(productAttribute.getStock());
			productAttribute.setStock(productAttribute.getStock());

		return productDetailRepository.save(productAttribute);
	}


}
