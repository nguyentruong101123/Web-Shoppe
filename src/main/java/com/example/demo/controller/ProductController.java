package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.example.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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


	
	private static final String UPLOADED_FOLDER = "src/main/resources/static/assets/images/product/";

	@ModelAttribute("product")
	public Product createProduct() {
		return new Product();
	}

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


	@PreAuthorize("isAuthenticated()")
	@GetMapping("/service/product")
	public String getProduct(Model model, @RequestParam(defaultValue = "1") Integer numPage) {
		model.addAttribute("hideHeader", true);
		services.addUserDetailsToModel(model);
		CustomUserDetail currentUserDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// Lấy Account từ CustomUserDetail
		Account currentUser = currentUserDetails.getAccount();

		Integer size = 5;
		Pageable pageable = PageRequest.of(numPage - 1, size);
		Page<Product> products = productService.findByUser(currentUser, pageable);

		model.addAttribute("listProduct", products);
		model.addAttribute("currentPage", numPage);
		model.addAttribute("totalPages", products.getTotalPages());

		return "product/create-product";
	}

	@GetMapping("/service/product/edit/")
	public String editProduct(Model model) {
		model.addAttribute("hideHeader", true);
		services.addUserDetailsToModel(model);
		return "product/update-product";
	}
	@GetMapping("/service/product/delete/{id}")
	public String deleteProduct(@PathVariable("id") Integer id) {
		productService.delete(id);
		return "redirect:/home/service/product";
	}

	@GetMapping("/service/product/edit/{productId}")
	public String getProductId(@PathVariable("productId") Integer productId, Model model) {
		services.addUserDetailsToModel(model);
		model.addAttribute("hideHeader", true);
		Product products = productService.getProductById(productId);
		model.addAttribute("categories", categoryService.findAll());

		if (products != null) {
			// Xử lý khi sản phẩm không tồn tại
			model.addAttribute("product", products);
		} else {
			// Xử lý khi sản phẩm không tồn tại, có thể thêm thông báo lỗi hoặc chuyển hướng
			model.addAttribute("error", "Product not found");
			// Bạn có thể chuyển hướng hoặc trả về một view khác nếu muốn
		}

		return "product/update-product";
	}


	@PostMapping("/service/product/update")
	public String update(@ModelAttribute("product") Product product,@RequestParam("imageMain") MultipartFile file,
						 @RequestParam("categoryId") Integer categoryId ,Model model
	) throws IOException {
		if (product.getId() == null) {
			throw new IllegalArgumentException("Product ID must not be null");
		}
		Category category = categoryService.findById(categoryId);
		if(category != null) {
			product.setCategory(category);
		}



//		if(file != null) {
//			String fileName = file.getOriginalFilename(); // Lấy tên file gốc
//			String uploadDir =  "assets/images/product/";
//			Path uploadPath = Paths.get(uploadDir);
//
//			if (!Files.exists(uploadPath)) {
//				Files.createDirectories(uploadPath);
//			}
//			try {
//				Files.copy(file.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
//			} catch (IOException e) {
//				System.err.println("Error copying file: " + fileName);
//				e.printStackTrace();
//			}
//			ProductImage productImage = new ProductImage();
//			productImage.setImage(fileName);
//			product.setMainImage(productImage);
//
//		}

		productService.update(product);

		return "redirect:/home/service/product";

	}



	@GetMapping("/service/product/detail")
	public String getProductDetail(Model model) {
		services.addUserDetailsToModel(model);
		model.addAttribute("hideHeader", true);

		return "product/product-detail";
	}

	@GetMapping("/service/product/detail/{id}")
	public String getProductDetail(@PathVariable("id") Integer id, Model model) {
		services.addUserDetailsToModel(model);
		model.addAttribute("hideHeader", true);
		List<ProductAttribute> productAttribute =productDetailService.findByProductId(id);
		if (productAttribute.isEmpty()) {
			model.addAttribute("errorMessage", "Không có chi tiết sản phẩm cho sản phẩm này.");
			return "product/product-detail"; // Trả về trang với thông báo lỗi hoặc xử lý khác
		}
		ProductAttribute findId = productDetailService.findById(productAttribute.get(0).getId());
		if (findId == null) {
			model.addAttribute("errorMessage", "Chi tiết sản phẩm không tìm thấy.");
			return "product/product-detail"; // Trả về trang với thông báo lỗi hoặc xử lý khác
		}

		List<ProductImage> productImages = productImageService.findByProductAttributeId(findId.getId());
		model.addAttribute("image", productImages);
		model.addAttribute("productAttribute", productAttribute);
		return "product/product-detail";
	}


	@GetMapping("/service/product/detail/delete/{id}")
	public String deleteProductDetail(@PathVariable("id") Integer id) {
		productDetailService.deleteProductAttribute(id);
		return "redirect:/home/service/product/detail";
	}




	@RequestMapping("/service/product/add")
	@PreAuthorize("isAuthenticated()")
	public String createProduct(Model model) {
		services.addUserDetailsToModel(model);
		model.addAttribute("hideHeader", true);
		model.addAttribute("categories", categoryService.findAll());
	    model.addAttribute("sizes", sizeService.getAll());
	    model.addAttribute("colors", colorService.getAll());
	    model.addAttribute("product", new Product());
		return "product/add-product";
	}

	
	
	@PostMapping("/product/add")
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

			List<ProductAttribute> createdAttributes = new ArrayList<>();

	        // Lưu các thuộc tính sản phẩm (màu, kích thước, giá, số lượng)
	        for (int i = 0; i < colorIds.size(); i++) {
	        	ProductAttribute productAttribute = new ProductAttribute();
	            productAttribute.setProduct(product);
	            productAttribute.setColor(new Color(colorIds.get(i)));
	            productAttribute.setSize(new Size(sizeIds.get(i)));
	            productAttribute.setPrice(attributePrices.get(i));
	            productAttribute.setStock(attributeStocks.get(i));
	            productDetailService.createProductAttribute(productAttribute);

				createdAttributes.add(productAttribute);
	        }

	        // Xử lý ảnh chính
	        if (!mainImageFile.isEmpty()) {
	            String mainImagePath = saveUploadedFile(mainImageFile);
	            ProductImage mainImage = new ProductImage();
	            mainImage.setProduct(product);
	   
	            mainImage.setImage(mainImagePath);
	            mainImage.setImageType("main");
//				if (!createdAttributes.isEmpty()) {
//					mainImage.setAttribute(createdAttributes.get(0)); // Gán ProductAttribute đầu tiên
//				}

	            productImageService.createProductImage(mainImage);
	        }

	        // Xử lý ảnh chi tiết
			if (detailImageFiles != null && detailImageFiles.length > 0) {
				for (int i = 0; i < detailImageFiles.length; i++) {
					MultipartFile file = detailImageFiles[i];
					if (!file.isEmpty()) {
						String detailImagePath = saveUploadedFile(file);
						ProductImage detailImage = new ProductImage();
//						detailImage.setProduct(product); // Liên kết ảnh với sản phẩm
						detailImage.setImage(detailImagePath);
						detailImage.setImageType("detail");

						// Liên kết với ProductAttribute nếu cần (ví dụ: dựa trên chỉ số)
						if (i < createdAttributes.size()) {
							detailImage.setAttribute(createdAttributes.get(i)); // Gán ProductAttribute tương ứng
						}

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

	public String updateUploadedFile(MultipartFile file) throws IOException {
		String directory = "src/main/resources/static/assets/images/product"; // Đường dẫn đến thư mục
		String fileName = file.getOriginalFilename();
		Path path = Paths.get(directory, fileName);

		// Xóa file cũ nếu nó tồn tại
		if (Files.exists(path)) {
			Files.delete(path); // Xóa file cũ
		}

		// Lưu file mới
		Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

		return fileName;
	}
}
