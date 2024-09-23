package com.example.demo.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "ProductAttributes", uniqueConstraints = @UniqueConstraint(columnNames = {"productId", "colorId", "sizeId"}))
public class ProductAttribute implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sizeId", nullable = false)
    private Size size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "colorId", nullable = false)
    private Color color;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Double price;

    @OneToMany(mappedBy = "attribute", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductImage> productImages;
}

//@SuppressWarnings("serial")
//@Data
//@Entity
//@Table(name = "ProductAttributes")
//public class ProductAttribute implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "productId")
//    private Product product;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "sizeId")
//    private Size size;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "colorId")
//    private Color color;
//
//    private Integer stock;
//    private Double price;
//
//    // Liên kết với ProductImage nếu có
//    @OneToMany(mappedBy = "attribute", fetch = FetchType.LAZY)
//    private List<ProductImage> productImages;
//}