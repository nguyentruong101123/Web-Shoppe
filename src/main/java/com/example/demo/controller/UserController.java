package com.example.demo.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.component.UserServices;
import com.example.demo.entity.Account;
import com.example.demo.entity.UserProfile;
import com.example.demo.service.impl.UserProfileServiceImpl;
import com.example.demo.services.AccountService;
import com.example.demo.services.UserProfileService;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/account")
public class UserController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserServices services;

	@Autowired
	private UserProfileService profileServiceImpl;

	@ModelAttribute
	public UserProfile profile() {
		return new UserProfile();
	}
	
	@GetMapping("/info/user/profile/{userId}")
	public String getUserProfile(@PathVariable Integer userId, Model model) {
	    UserProfile userProfile = profileServiceImpl.getUserProfileByUserId(userId);
	    model.addAttribute("hideHeader", true);
	    if (userProfile != null) {
	        model.addAttribute("userProfile", userProfile);
	    }
	    model.addAttribute("userId", userId); // Truyền userId vào model khi userProfile == null
	    return "profile/user";
	}

	
	@RequestMapping("/user/profile/{id}")
	public String profile(@PathVariable Integer id, Model model) {
		Account account = accountService.findById(id);
	    model.addAttribute("hideHeader", true);
		if (account != null) {
			model.addAttribute("account", account);
			 UserProfile userProfileOptional = profileServiceImpl.getUserProfileByUserId(id);
		        if (userProfileOptional != null) {
		            model.addAttribute("userProfile", userProfileOptional);
		        } else {
		            model.addAttribute("errorMessage", "UserProfile not found");
		        }
			
		} else {
			model.addAttribute("errorMessage", "Account not found");
			return "profile/user"; // Trả về sớm nếu account không tồn tại
		}
		return "profile/user";
	}

	@PostMapping("/update/address")
	public String updateUser(@ModelAttribute @Valid UserProfile userProfile, BindingResult bindingResult, Model model)
			throws Exception {
		if (bindingResult.hasErrors()) {
			model.addAttribute("errorMessage", "Validation failed");
			return "profile/user";
		}

		profileServiceImpl.update(userProfile.getUserId(), userProfile);
		return "redirect:/account/user/profile/" + userProfile.getUserId();
	}
	

	
	@PostMapping("/create/address")
	public String createUser(@RequestParam("accountId") Integer accountId,
							 @RequestParam("streetAddress") String streetAddress,
							 @RequestParam("city") String city,
							 @RequestParam("state") String state,
							 @RequestParam("zipCode") String zipCode,
							 @RequestParam("country") String country,
							 @RequestParam("phoneNumber") String phoneNumber,
							 @RequestParam("dateOfBirth") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfBirth,
							 @RequestParam("placeOfBirth") String placeOfBirth,
						 	 UserProfile profile, Model model)
	        throws Exception {


		if (profile.getDateOfBirth() == null) {
			model.addAttribute("errorMessage", "Date of Birth is required");
			return "profile/user";
		}
	    if (accountId == null) {
	        // Nếu không có userId thì tạo mới hồ sơ người dùng
			throw new Exception("Account Id is required");
	    } else {
	        // Nếu đã có userId thì cập nhật thông tin hiện có
			profile.setUserId(accountId);
			profile.setStreetAddress(streetAddress);
			profile.setCity(city);
			profile.setState(state);
			profile.setZipCode(zipCode);
			profile.setCountry(country);
			profile.setPhoneNumber(phoneNumber);
			profile.setDateOfBirth(dateOfBirth);
			profile.setPlaceOfBirth(placeOfBirth);
			profileServiceImpl.createUserProfile(profile); // Phương thức này cần tồn tại trong Service để lưu đối tượng mới
	    }

	    return "redirect:/account/user/profile/" + profile.getUserId();
	}

	@PostMapping("/update/username/{username}")
	public String updateUser(
	        @ModelAttribute @Valid Account account,
	        @RequestParam("photoFile") MultipartFile photoFile,
	        BindingResult bindingResult,
	        Model model) {

	    // Kiểm tra xem có lỗi validate không
	    if (bindingResult.hasErrors()) {
	        // In ra lỗi cụ thể
	        bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
	        
	        model.addAttribute("errorMessage", "Validation failed. Please correct the errors below.");
	        return "profile/user"; // Trở về trang chỉnh sửa khi có lỗi
	    }

	    // Kiểm tra username có rỗng không
	    if (account.getUsername() == null || account.getUsername().isEmpty()) {
	        model.addAttribute("errorMessage", "Username must not be null or empty.");
	        return "profile/user";
	    }

	    try {
	        // Xử lý tệp upload nếu nó không rỗng
	    	if (!photoFile.isEmpty()) {
	            String fileName = photoFile.getOriginalFilename(); // Lấy tên file gốc
	            String uploadDir = "assets/images/avatas/";

	            // Tạo đường dẫn lưu ảnh
	            Path uploadPath = Paths.get(uploadDir);

	            if (!Files.exists(uploadPath)) {
	                Files.createDirectories(uploadPath);
	            }

	            // Lưu file vào thư mục với tên gốc
	            Files.copy(photoFile.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

	            // Cập nhật tên file vào thuộc tính photo của account
	            account.setPhoto(fileName);
	        }

	        // Gọi service để cập nhật account
	        Account updatedAccount = accountService.update(account);
	        model.addAttribute("successMessage", "Profile updated successfully.");
	        return "redirect:/home/index";

	    } catch (IOException e) {
	        model.addAttribute("errorMessage", "An error occurred while uploading the image: " + e.getMessage());
	        return "profile/user";
	    }
	}





}


