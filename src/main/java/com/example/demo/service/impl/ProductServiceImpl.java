package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Account;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
	public Page<Product> findByCategoryId(Integer categoryId,Pageable pageable) {
		return productRepository.findByCategoryId(categoryId,pageable);
	}

	@Override
	public List<Product> getAll() {
		return productRepository.findAll();
	}

	@Override
	public Product getProductById(Integer productId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

		// Lấy hình ảnh chính cho sản phẩm
		ProductImage mainImage = productImageRepository.findMainImageByProductId(product.getId());
		product.setMainImage(mainImage);

		return product; // Trả về sản phẩm với hình ảnh chính
	}

	@Override
	public Page<Product> findByCategoryIdWithMainImage(Integer categoryId, Pageable pageable) {
		Page<Product> products = productRepository.findByCategoryId(categoryId, pageable);
		for (Product product : products) {
			ProductImage mainImage = productImageRepository.findMainImageByProductId(product.getId());
			product.setMainImage(mainImage);
		}
		return products;
	}

	@Override
	public Page<Product> getAllWithMainImage(Pageable pageable) {
		Page<Product> productPage = productRepository.findAll(pageable);
	    List<Product> products = productPage.getContent();  // Chuyển thành List<Product>

	    // Thiết lập ảnh chính cho từng sản phẩm
	    for (Product product : products) {
	        ProductImage mainImage = productImageRepository.findMainImageByProductId(product.getId());
	        product.setMainImage(mainImage);
	    }

	    return new PageImpl<>(products, pageable, productPage.getTotalElements());
	}

	@Override
	public Page<Product> findByUser(Account account, Pageable pageable) {
		Page<Product> productPage = productRepository.findByUser(account ,pageable);
		List<Product> products = productPage.getContent();  // Chuyển thành List<Product>

		// Thiết lập ảnh chính cho từng sản phẩm
		for (Product product : products) {
			ProductImage mainImage = productImageRepository.findMainImageByProductId(product.getId());
			product.setMainImage(mainImage);
		}

		return new PageImpl<>(products, pageable, productPage.getTotalElements());
	}

	@Override
	public Product createProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public Product update(Product product) {
		Optional<Product> optionalProduct = productRepository.findById(product.getId());

		// Kiểm tra nếu sản phẩm tồn tại
		if (!optionalProduct.isPresent()) {
			throw new EntityNotFoundException("Product with ID " + product.getId() + " not found");
		}

		Product newProduct = optionalProduct.get();
		newProduct.setCategory(product.getCategory());
		newProduct.setName(product.getName());
		newProduct.setDescription(product.getDescription());
		newProduct.setPrice(product.getPrice());
		if (product.getMainImage() != null) {
			newProduct.setMainImage(product.getMainImage());
		}
		return productRepository.save(newProduct);

	}

	@Override
	public int getTotalPages(int pageSize) {
        long totalProducts = productRepository.count(); // Lấy tổng số sản phẩm
        return (int) Math.ceil((double) totalProducts / pageSize); // Tính tổng số trang
    }

	@Override
	public Product delete(Integer productId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));;
		productRepository.delete(product);
		return product;
	}

}
