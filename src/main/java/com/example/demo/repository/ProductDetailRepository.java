package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ProductAttribute;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductAttribute, Integer>{
	
    @Query("SELECT pa FROM ProductAttribute pa "
            + "JOIN FETCH pa.product p "
            + "JOIN FETCH pa.size s "
            + "JOIN FETCH pa.color c "
            + "LEFT JOIN FETCH pa.productImages img "
            + "WHERE p.id = :productId")
	    List<ProductAttribute> findByProductId(@Param("productId") Integer productId);
	
}
