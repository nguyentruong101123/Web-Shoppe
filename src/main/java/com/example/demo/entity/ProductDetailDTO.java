package com.example.demo.entity;

import lombok.Data;

import java.util.List;

@Data
public class ProductDetailDTO {
    private Product product;
    private Account account;
    private List<ProductImage> productImages;
    private List<Size> sizes;
    private List<Color> colors;
    private List<ProductAttribute> productAttributes;
}
