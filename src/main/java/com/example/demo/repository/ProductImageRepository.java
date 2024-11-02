package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ProductImage;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {

	@Query("SELECT pi FROM ProductImage pi WHERE pi.product.id = :productId AND pi.attribute.id IS NULL AND pi.imageType = 'main'")
	ProductImage getMainImageByProductId(Integer productId);

	@Query("select pi from ProductImage pi where pi.attribute.id = :productAttributeId and pi.imageType = 'detail'")
	List<ProductImage> findByAttributeId(@Param("productAttributeId") Integer productAttributeId);

	@Query("select pi from ProductImage pi where pi.attribute.id = :productAttributeId and pi.imageType = 'detail'")
	ProductImage findFirstByAttributeId(@Param("productAttributeId") Integer productAttributeId);

	List<ProductImage> findByProductId(Integer productId);
	
	@Query("SELECT pi FROM ProductImage pi WHERE pi.product.id = :productId AND pi.imageType = 'main'")
	ProductImage findMainImageByProductId(@Param("productId") Integer productId);


}