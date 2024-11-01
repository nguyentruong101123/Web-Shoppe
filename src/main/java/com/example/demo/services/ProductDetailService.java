package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.ProductAttribute;

public interface ProductDetailService {
	
	 List<ProductAttribute> findByProductId(Integer productId);

	 ProductAttribute createProductAttribute(ProductAttribute productAttribute);

	 ProductAttribute findById(Integer id);

	 ProductAttribute deleteProductAttribute(Integer id);



}
