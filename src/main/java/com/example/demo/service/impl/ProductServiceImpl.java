package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductImage;
import com.example.demo.repository.ProductImageRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	private ProductImageRepository productImageRepository;

	@Override
	public List<Product> findByCategoryIdAndProductImageId(Integer categoryId, Integer productImageId) {
		return productRepository.findByCategoryIdAndProductImageId(categoryId, productImageId);
	}

	@Override
	public List<Product> findByCategoryId(Integer categoryId,Pageable pageable) {
		return productRepository.findByCategoryId(categoryId,pageable);
	}

	@Override
	public List<Product> getAll() {
		return productRepository.findAll();
	}

	@Override
	public Product getProductById(Integer productId) {
		return productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
	}

	@Override
	public List<Product> findByCategoryIdWithMainImage(Integer categoryId, Pageable pageable) {
		List<Product> products = productRepository.findByCategoryId(categoryId, pageable);
		for (Product product : products) {
			ProductImage mainImage = productImageRepository.findMainImageByProductId(product.getId());
			product.setMainImage(mainImage);
		}
		return products;
	}

	@Override
	public List<Product> getAllWithMainImage(Pageable pageable) {
		Page<Product> productPage = productRepository.findAll(pageable);
	    List<Product> products = productPage.getContent();  // Chuyển thành List<Product>

	    // Thiết lập ảnh chính cho từng sản phẩm
	    for (Product product : products) {
	        ProductImage mainImage = productImageRepository.findMainImageByProductId(product.getId());
	        product.setMainImage(mainImage);
	    }

	    return products;
	}

	@Override
	public Product createProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public int getTotalPages(int pageSize) {
        long totalProducts = productRepository.count(); // Lấy tổng số sản phẩm
        return (int) Math.ceil((double) totalProducts / pageSize); // Tính tổng số trang
    }

}
