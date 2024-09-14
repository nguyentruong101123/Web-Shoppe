package com.example.demo.services;

import java.util.List;

import com.example.demo.entity.ProductAttribute;

public interface ProductDetailService {
	
	public List<ProductAttribute> findByProductId(Integer productId);
	
	public ProductAttribute createProductAttribute(ProductAttribute productAttribute);

}
