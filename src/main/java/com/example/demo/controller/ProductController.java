package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.component.UserServices;
import com.example.demo.entity.Account;
import com.example.demo.entity.Category;
import com.example.demo.entity.Color;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductAttribute;
import com.example.demo.entity.ProductImage;
import com.example.demo.entity.Size;
import com.example.demo.service.impl.AccountServiceImpl;
import com.example.demo.services.CategoryService;
import com.example.demo.services.ColorService;
import com.example.demo.services.ProductDetailService;
import com.example.demo.services.ProductImageService;
import com.example.demo.services.ProductService;
import com.example.demo.services.SizeService;

@Controller
@RequestMapping("/home")
public class ProductController {

	@Autowired
	private UserServices services;

	@Autowired
	private CategoryService categoryService;
	  
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductDetailService productDetailService;

	@Autowired
	private ProductImageService productImageService;
	
	@Autowired
	private SizeService sizeService;

	@Autowired
	private ColorService colorService;
	
	@Autowired
    private AccountServiceImpl accountServiceImpl;
	
	private static final String UPLOADED_FOLDER = "src/main/resources/static/assets/images/avatas/";

	@RequestMapping("/index")
	public String home(Model model, @RequestParam(defaultValue = "1") Integer num) {
	    // Thêm thông tin người dùng vào model
	    services.addUserDetailsToModel(model);
	    model.addAttribute("hideHeader", false);

	    // Tạo Pageable object với thông tin trang hiện tại và kích thước
		// Khởi tạo Page<Product> dựa trên categoryId
	    Integer size = 12;
    	Pageable pageable = PageRequest.of(num-1, size);
    	Page<Product> products;

		products = productService.getAllWithMainImage(pageable);

	    // Thêm danh sách sản phẩm vào model
		// Thêm danh sách sản phẩm vào model
		model.addAttribute("products", products);
		model.addAttribute("currentPage", num);                         // Trang hiện tại
		model.addAttribute("totalPages", products.getTotalPages());      // Tổng số trang

		return "product/list";
	}
	

	@GetMapping("/service/product")
	public String getProduct(Model model, @RequestParam(defaultValue = "1") Integer num) {
		model.addAttribute("hideHeader", true);
		services.addUserDetailsToModel(model);

		Integer size = 12;
		Pageable pageable = PageRequest.of(num - 1, size);
		Page<Product> products = productService.getAllWithMainImage(pageable);

		model.addAttribute("products", products);
		model.addAttribute("currentPage", num);
		model.addAttribute("totalPages", products.getTotalPages());

		return "product/create-product";
	}

	
	
	@RequestMapping("/service/product")
	@PreAuthorize("isAuthenticated()")
	public String createProduct(Model model) {
		services.addUserDetailsToModel(model);
		model.addAttribute("hideHeader", true);
		model.addAttribute("categories", categoryService.findAll());
	    model.addAttribute("sizes", sizeService.getAll());
	    model.addAttribute("colors", colorService.getAll());
	    model.addAttribute("product", new Product());
		return "product/create-product";
	}
	
	
	
	@PostMapping("/product/create")
	public String createProduct(
	        @RequestParam String name,
	        @RequestParam String description,
	        @RequestParam Double price,
	        @RequestParam Integer stock,
	        @RequestParam Integer categoryId,
	        @RequestParam("colorIds") List<Integer> colorIds,
	        @RequestParam("sizeIds") List<Integer> sizeIds,
	        @RequestParam("attributeStocks") List<Integer> attributeStocks,
	        @RequestParam("attributePrices") List<Double> attributePrices,
	        @RequestParam("mainImage") MultipartFile mainImageFile,
	        @RequestParam("detailImages") MultipartFile[] detailImageFiles,
	        Model model) {

	    try {
	        // Lấy thông tin người dùng hiện tại
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String username = authentication.getName();
	        Account user = accountServiceImpl.findAccountByUsername(username);

	        // Tạo sản phẩm mới
	        Product product = new Product();
	        product.setName(name);
	        product.setDescription(description);
	        product.setPrice(price);
	        product.setStock(stock);
	        product.setCategory(new Category(categoryId));
	        product.setUser(user);
	        productService.createProduct(product);

	        // Lưu các thuộc tính sản phẩm (màu, kích thước, giá, số lượng)
	        for (int i = 0; i < colorIds.size(); i++) {
	        	ProductAttribute productAttribute = new ProductAttribute();
	            productAttribute.setProduct(product);
	            productAttribute.setColor(new Color(colorIds.get(i)));
	            productAttribute.setSize(new Size(sizeIds.get(i)));
	            productAttribute.setPrice(attributePrices.get(i));
	            productAttribute.setStock(attributeStocks.get(i));
	            productDetailService.createProductAttribute(productAttribute);
	         
	        }

	        // Xử lý ảnh chính
	        if (!mainImageFile.isEmpty()) {
	            String mainImagePath = saveUploadedFile(mainImageFile);
	            ProductImage mainImage = new ProductImage();
	            mainImage.setProduct(product);
	   
	            mainImage.setImage(mainImagePath);
	            mainImage.setImageType("main");
	            productImageService.createProductImage(mainImage);
	        }

	        // Xử lý ảnh chi tiết
	        if (detailImageFiles != null && detailImageFiles.length > 0) {
	            for (MultipartFile file : detailImageFiles) {
	                if (!file.isEmpty()) {
	                    String detailImagePath = saveUploadedFile(file);
	                    ProductImage detailImage = new ProductImage();
	                    detailImage.setProduct(product);
	                   
	                    detailImage.setImage(detailImagePath);
	                    detailImage.setImageType("detail");
	                    productImageService.createProductImage(detailImage);
	                }
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return "redirect:/home/index";
	}



	
	 private String saveUploadedFile(MultipartFile file) throws IOException {
	        Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
	        Files.write(path, file.getBytes());
	        return file.getOriginalFilename();
	    }
	
	
}

//@PostMapping("/product/create")
//public String createProduct(
//        @RequestParam String productName,
//        @RequestParam Double productPrice,
//        @RequestParam List<String> sizes,
//        @RequestParam List<String> colors,
//        @RequestParam List<Integer> stocks) {
//
//    // Tạo sản phẩm
//    Product product = new Product();
//    product.setName(productName);
//    product.setPrice(productPrice);
//    productService.save(product);
//
//    // Tạo thuộc tính sản phẩm
//    for (int i = 0; i < sizes.size(); i++) {
//        ProductAttribute attribute = new ProductAttribute();
//        attribute.setProduct(product);
//        attribute.setSize(sizeService.findOrCreate(sizes.get(i))); // Tìm hoặc tạo mới kích thước
//        attribute.setColor(colorService.findOrCreate(colors.get(i))); // Tìm hoặc tạo mới màu sắc
//        attribute.setStock(stocks.get(i));
//        productAttributeService.save(attribute);
//    }
//
//    return "redirect:/product/list";
//}

