package com.example.demo.service.impl;

import java.util.List;

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

}
