package com.example.demo.services;

import java.util.List;

import com.example.demo.entity.ProductImage;

public interface ProductImageService {
	
	List<ProductImage> getImagesByProductId(Integer productId);
	
	ProductImage getMainImageByProductId(Integer productId);
	
	ProductImage createProductImage(ProductImage productImage);

	ProductImage updateProductImage(ProductImage productImage);

	List<ProductImage> findByProductAttributeId(Integer productId);

	ProductImage deleteProductImage(Integer id);

	ProductImage findByAttributeId(Integer id);
}
