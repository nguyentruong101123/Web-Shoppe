package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ProductAttribute;
import com.example.demo.repository.ProductDetailRepository;
import com.example.demo.services.ProductDetailService;

@Service
public class ProductDetailServiceImpl implements ProductDetailService{

	@Autowired
	ProductDetailRepository detailRepository;
	
	@Override
	public List<ProductAttribute> findByProductId(Integer id) {
		return detailRepository.findByProductId(id);
	}

	@Override
	public ProductAttribute createProductAttribute(ProductAttribute productAttribute) {
		return detailRepository.save(productAttribute);
	}

	@Override
	public ProductAttribute findById(Integer id) {
		return detailRepository.findById(id).orElseThrow(null);
	}

	@Override
	public ProductAttribute deleteProductAttribute(Integer id) {
		ProductAttribute productAttribute = detailRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Product not found with id: " + id));;
		detailRepository.delete(productAttribute);
		return productAttribute;
	}

}
