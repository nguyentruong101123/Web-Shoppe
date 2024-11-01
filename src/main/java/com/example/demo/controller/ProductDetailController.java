package com.example.demo.controller;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.component.UserServices;
import com.example.demo.entity.Account;
import com.example.demo.entity.Color;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductAttribute;
import com.example.demo.entity.ProductDetailDTO;
import com.example.demo.entity.ProductImage;
import com.example.demo.entity.Size;
import com.example.demo.services.ProductDetailService;
import com.example.demo.services.ProductImageService;
import com.example.demo.services.ProductService;

@Controller
@RequestMapping("/product")
public class ProductDetailController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductDetailService productDetailService;
	
	@Autowired
	private UserServices services;
	
	@Autowired
	private ProductImageService productImageService;
	
	public String formatCurrency(double amount) {
	    Locale locale = new Locale("vi", "VN");
	    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
	    return currencyFormatter.format(amount);
	}

	
	@GetMapping("/details/{id}")
	public String productDetail(@PathVariable("id") Integer productId, Model model) {
		services.addUserDetailsToModel(model);
		model.addAttribute("hideHeader", true);
		ProductDetailDTO productDetailDTO = new ProductDetailDTO();
		
		Product product = productService.getProductById(productId);
		Account account = product.getUser();
		
		List<ProductAttribute> productAttributes = productDetailService.findByProductId(productId);
		
//		ProductImage mainImage = productImageService.getMainImageByProductId(productId);
		
		List<ProductImage> productImages = productImageService.getImagesByProductId(productId);
		List<Size> sizes = productAttributes.stream()
											.map(ProductAttribute::getSize)
											.distinct()
											.collect(Collectors.toList());
		
		List<Color> colors = productAttributes.stream()
				.map(ProductAttribute::getColor)
				.distinct()
				.collect(Collectors.toList());
		
		  for (Color color : colors) {
		        List<ProductAttribute> attributesForColor = productAttributes.stream()
		            .filter(attr -> attr.getColor().getId().equals(color.getId()))
		            .collect(Collectors.toList());
		        color.setProductAttributes(attributesForColor);
		    }
		
		productDetailDTO.setProduct(product);
		productDetailDTO.setProductImages(productImages);
		productDetailDTO.setSizes(sizes);
		productDetailDTO.setColors(colors);
		productDetailDTO.setProductAttributes(productAttributes);
		productDetailDTO.setAccount(account);
		
		
		List<String> formattedPrices = productAttributes.stream()
			    .map(attribute -> formatCurrency(attribute.getPrice()))
			    .collect(Collectors.toList());

	    model.addAttribute("formattedPrices", formattedPrices);
		
		model.addAttribute("details", productDetailDTO);
		return "product/detail";
		
	}


}
