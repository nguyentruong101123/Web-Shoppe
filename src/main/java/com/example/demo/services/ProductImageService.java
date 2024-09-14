package com.example.demo.services;

import java.util.List;

import com.example.demo.entity.ProductImage;

public interface ProductImageService {
	
	public List<ProductImage> getImagesByProductId(Integer productId);
	
	public ProductImage getMainImageByProductId(Integer productId);
	
	public ProductImage createProductImage(ProductImage productImage);
}
